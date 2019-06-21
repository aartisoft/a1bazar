package com.pratilipi.hackathon.unbranded;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.androidnetworking.error.ANError;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pratilipi.hackathon.unbranded.home.HomeActivity;
import com.pratilipi.hackathon.unbranded.network.ApiEndPoint;
import com.pratilipi.hackathon.unbranded.network.model.UserProduct;
import com.pratilipi.hackathon.unbranded.rxjava.AppSchedulerProvider;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class LogInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9003;
    private final String TAG = LogInActivity.class.getSimpleName();
    @BindView(R.id.image)
    KenBurnsView kenBurnsView;
    @BindView(R.id.login_layout)
    RelativeLayout loginLayout;
    private FirebaseAuth mAuth;
    // ...
// Initialize Firebase Auth
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient.Builder mGoogleApiClient;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        kenBurnsView.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }
        });

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    signIn();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            startHomeActivity();
//        updateUI(currentUser);
    }

    private void startHomeActivity() {
        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }

//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
//                // ...
//            }
//        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(TAG, "onComplete: user" + user.getDisplayName());

                                updateToServer(user);

                                //                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Snackbar.make(kenBurnsView, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                                //                            updateUI(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // ...
                    }
                });
    }

    private void updateToServer(FirebaseUser user) {
        FirebaseInstanceId.getInstance().
                getInstanceId().addOnSuccessListener(instanceIdResult -> {
            try {
                String fcmToken = instanceIdResult.getToken();
                Log.d(TAG, "onComplete: fcmToken" + fcmToken);

                new CompositeDisposable().add(postUserData(user, fcmToken)
                        .subscribeOn(new AppSchedulerProvider().io())
                        .observeOn(new AppSchedulerProvider().ui())
                        .subscribe(new Consumer<UserProduct>() {
                            @Override
                            public void accept(UserProduct userProduct) throws Exception {
                                if (userProduct != null) {
                                    Log.d(TAG, "accept: " + user.getEmail());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // handle the error here
                                if (throwable instanceof ANError) {
                                    ANError anError = (ANError) throwable;
                                    Log.d("anError ", anError.toString());
//                            handleApiError(anError);
                                }
                            }
                        }));


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        startHomeActivity();

    }

    private Observable<UserProduct> postUserData(FirebaseUser user, String fcmToken) {
        HashMap<String, String> bodyParameterMap = new HashMap<>();
        if (user.getPhotoUrl() != null)
            bodyParameterMap.put("imageUrl", user.getPhotoUrl().toString());

        bodyParameterMap.put("email", user.getEmail());
        bodyParameterMap.put("name", user.getDisplayName());
        bodyParameterMap.put("fcm", fcmToken);

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_USER)
//                .addHeaders("user-Id", "3")
                .addUrlEncodeFormBodyParameter(bodyParameterMap)
                .build()
                .getObjectObservable(UserProduct.class);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
