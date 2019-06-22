package com.pratilipi.hackathon.unbranded.home.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.error.ANError;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.detail.DetailActivity;
import com.pratilipi.hackathon.unbranded.network.ApiEndPoint;
import com.pratilipi.hackathon.unbranded.network.model.Product;
import com.pratilipi.hackathon.unbranded.network.model.UserProduct;
import com.pratilipi.hackathon.unbranded.recording.RecordingActivity;
import com.pratilipi.hackathon.unbranded.rxjava.AppSchedulerProvider;
import com.pratilipi.hackathon.unbranded.utils.AppConstants;
import com.pratilipi.hackathon.unbranded.utils.DialogFactory;
import com.pratilipi.hackathon.unbranded.utils.ScreenUtils;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;

/**
 *
 */
public class NavCreateFragment extends Fragment implements VideoRendererEventListener {

    public static final String TAG = NavCreateFragment.class.getSimpleName();
    public static final int REQUEST_PERMISSION_STORAGE = 3;
    private static final int SELECTED_PIC = 1;
    Unbinder unbinder;
    @BindView(R.id.add_img_btn)
    LinearLayout addImgBtn;
    @BindView(R.id.selectes_img)
    AppCompatImageView selectedImg;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.btn_camera)
    LottieAnimationView btnCamera;
    @BindView(R.id.add_audio)
    TextView addAudio;
    @BindView(R.id.play_layout)
    LinearLayout playLayout;

    @BindView(R.id.product_upload_form)
    LinearLayout productUploadFormLayout;

    @BindView(R.id.product_upload_form_name)
    TextInputEditText productName;

    @BindView(R.id.product_upload_form_description)
    TextInputEditText productDescription;

    @BindView(R.id.product_upload_form_price)
    TextInputEditText productPrice;

    @BindView(R.id.upload_product)
    TextView productUploadBtn;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    private String imageFileName;
    private Drawable mBitmapDrawable;
    private String imageUri;
    private PlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private String productVideoUrl;

    public static NavCreateFragment newInstance(int index) {
        NavCreateFragment fragment = new NavCreateFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_create, container, false);
        unbinder = ButterKnife.bind(this, view);

        simpleExoPlayerView = view.findViewById(R.id.player_view);


        addImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPermissions();
            }
        });


        selectedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImgBtn.performClick();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImgBtn.performClick();
            }
        });
        addAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecordingActivity();
            }
        });
        productUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProduct();
            }
        });

        btnCamera.playAnimation();



//        initPlayer("");
        return view;

    }

    @SuppressLint("CheckResult")
    private void checkPermission(RxPermissions rxPermissions) {
        try {
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO)
                    .subscribe(granted -> {
                        try {
                            if (granted) {
                                // All requested permissions are granted
                            } else {
                                Toast.makeText(getContext(), "Permission Required", Toast.LENGTH_SHORT).show();
                                checkPermission(rxPermissions);
                                // At least one permission is denied
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createProduct() {
        String name = productName.getText().toString();
        String description = productDescription.getText().toString();
        String price = productPrice.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Saving Product...");
        progressDialog.show();

        (new CompositeDisposable()).add(postProduct(name, description, price, productVideoUrl)
                .subscribeOn(new AppSchedulerProvider().io())
                .observeOn(new AppSchedulerProvider().ui())
                .subscribe(new Consumer<UserProduct>() {
                    @Override
                    public void accept(UserProduct userProduct) throws Exception {
                        progressDialog.dismiss();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(AppConstants.EXTRA_PRODUCT, (Serializable) userProduct.getProductList().get(0));
                        intent.putExtra(AppConstants.URL, productVideoUrl);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                    hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            Log.d("anError ", anError.toString());
//                            handleApiError(anError);
                        }
                    }
                }));
    }

    private void startRecordingActivity() {
        Log.d(TAG, "startRecordingActivity: ");

        String getAccountsPermission = Manifest.permission.RECORD_AUDIO;
        if (!hasPermission(getAccountsPermission)) {
            requestPermissionsSafely(new String[]{getAccountsPermission}
            );
        } else {
            Intent intent = new Intent(getContext(), RecordingActivity.class);
            intent.putExtra(AppConstants.EXTRA_IMAGE_PATH, imageUri);
            startActivityForResult(intent, AppConstants.REQUEST_CODE_RECORDER);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getPermissions() {

        String getAccountsPermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (!hasPermission(getAccountsPermission)) {
            requestPermissionsSafely(new String[]{getAccountsPermission}
            );
        } else {
            startUCropActivity();
        }

    }

    private void startUCropActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);

    }

    @TargetApi(Build.VERSION_CODES.M)
    protected boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermissionsSafely(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PERMISSION_STORAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case AppConstants.REQUEST_CODE_RECORDER:
                if (resultCode == RESULT_OK) {
                    String path = data.getStringExtra(AppConstants.EXTRA_VID_PATH);

                    Log.d(TAG, "onActivityResult: path" + path);
//                    initPlayer(path);
                    uploadImage(path);

                }
                break;

            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};


                    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);

                    String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    imageFileName = "Temp_" + timeStamp + "_" + ".jpg";

                    File folder = new File(Environment.getExternalStorageDirectory() + "/TextEditor");
                    File file = new File(root + "TextEditor" + File.separator + imageFileName);

                    UCrop.Options options = new UCrop.Options();
                    options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                    options.setCompressionQuality(50);
                    options.setFreeStyleCropEnabled(true);
                    options.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }
                    if (success) {
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                                //f.createNewFile();
                                int maxWidth = ScreenUtils.getScreenWidth(getActivity());
                                int maxHeight = ScreenUtils.getScreenHeight(getActivity());
                                Uri sourceUri = uri;
                                Uri destinationUri = Uri.fromFile(file);
                                UCrop.of(sourceUri, destinationUri)
//                                        .withAspectRatio(16, 9)
                                        .withOptions(options)
                                        .withMaxResultSize(maxWidth, maxHeight)
                                        .start(getActivity(), this);
//                                copyFile(new File(filepath), file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }


//                    addImageBetweentext(drawable);
                }
                break;

            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    Drawable drawable;
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(resultUri);
                        drawable = Drawable.createFromStream(inputStream, resultUri.toString());
                        mBitmapDrawable = drawable;

                    } catch (FileNotFoundException e) {
                        drawable = getResources().getDrawable(R.drawable.ic_launcher);
                    }


                    Log.d("filepath", resultUri.toString());
                    Log.d("filepath", imageFileName);
                    imageUri = resultUri.toString();


                    if (mBitmapDrawable != null) {
                        processBitmap(mBitmapDrawable);
                    }

                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            default:
                break;
        }

    }

    private void showProductForm(String url) {
        productVideoUrl = url;
        productUploadFormLayout.setVisibility(View.VISIBLE);
    }

    private void initPlayer(final String path) {

//        path = "https://firebasestorage.googleapis.com/v0/b/androidchatapp-2849a.appspot.com/o/video%2FBulBul%20Shop%20_Handmade%20Wooden%20Clutch_%20Bags%20_%E0%A4%A1%E0%A4%BF%E0%A4%9C%E0%A4%BC%E0%A4%BE%E0%A4%87%E0%A4%A8%E0%A4%B0%20HandBag.mp4?alt=media&token=d5680977-e6dc-42ed-9f55-e61acd332b9c";

        selectedImg.setVisibility(View.VISIBLE);
        playLayout.setVisibility(View.VISIBLE);
        simpleExoPlayerView.setVisibility(View.GONE);

        playLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product();
                product.setVideoUrl(path);
                product.setName("");
                product.setDescription("");
                product.setPrice(112);
                product.setViewCount(1234);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(AppConstants.EXTRA_PRODUCT, (Serializable) product);
                startActivity(intent);

            }
        });

       /* Uri mp4VideoUri = Uri.parse(path); //ABC NEWS

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(); //test

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        simpleExoPlayerView = new PlayerView(getActivity());

        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);


        int h = simpleExoPlayerView.getResources().getConfiguration().screenHeightDp;
        int w = simpleExoPlayerView.getResources().getConfiguration().screenWidthDp;
        Log.v(TAG, "height : " + h + " weight: " + w);
        simpleExoPlayerView.setUseController(false);//set to true or false to see controllers
        simpleExoPlayerView.requestFocus();
        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(),
                "testExoplayer"), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4VideoUri);
        final LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource);
        // Prepare the player with the source.
        player.prepare(videoSource);

        player.addListener(new ExoPlayer.EventListener() {


            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                Log.v(TAG, "Listener-onTracksChanged... ");
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.v(TAG, "Listener-onPlayerStateChanged..." + playbackState + "|||isDrawingCacheEnabled():" +
                        simpleExoPlayerView.isDrawingCacheEnabled());
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.v(TAG, "Listener-onPlayerError...");
                player.stop();
                player.prepare(loopingSource);
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.setPlayWhenReady(true); //run file/link when ready to play.
        player.setVideoDebugListener(this);
*/

    }

    private void processBitmap(Drawable bitmapDrawable) {
        int height = ScreenUtils.getScreenHeight(getActivity());
        int width = ScreenUtils.getScreenWidth(getActivity());

        selectedImg.getLayoutParams().height = width;
        selectedImg.getLayoutParams().width = width;
        selectedImg.requestLayout();
//
        selectedImg.setImageDrawable(bitmapDrawable);
        addImgBtn.setVisibility(View.GONE);

        selectedImg.setVisibility(View.VISIBLE);
        addAudio.setVisibility(View.VISIBLE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startUCropActivity();
                } else {
                    DialogFactory.createSimpleOkErrorDialog(getActivity(),
                            R.string.title_permissions,
                            R.string.permission_not_accepted_storage).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private Observable<UserProduct> postProduct(String name, String description, String price, String productVideoUrl) {

        Map<String, String> request = new HashMap<String, String>();
        request.put(AppConstants.PRODUCT_NAME, name);
        request.put(AppConstants.PRODUCT_DESCRIPTION, description);
        request.put(AppConstants.PRODUCT_PRICE, price);
        request.put(AppConstants.PRODUCT_VIDEO_URL, productVideoUrl);

        Map<String, String> header = new HashMap<String, String>();
        header.put(AppConstants.USER_ID, "4");

        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_PRODUCT_CREATE)
                .addHeaders(header)
                .addUrlEncodeFormBodyParameter(request)
                .build()
                .getObjectObservable(UserProduct.class)
                ;
    }

    private void uploadImage(String filePath) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("video/" + UUID.randomUUID().toString());
            ref.putFile(Uri.fromFile(new File(filePath)))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Uploaded", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onSuccess: ref.getDownloadUrl();" + ref.getDownloadUrl());
                            Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                            firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    Log.e("TAG:", "the url is: " + url);

                                    String ref = storageReference.getName();
                                    Log.e("TAG:", "the ref is: " + ref);
                                    showProductForm(url);
                                    initPlayer(url);
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    @Override
    public void onVideoEnabled(DecoderCounters counters) {

    }

    @Override
    public void onVideoDecoderInitialized(String decoderName, long initializedTimestampMs, long initializationDurationMs) {

    }

    @Override
    public void onVideoInputFormatChanged(Format format) {

    }

    @Override
    public void onDroppedFrames(int count, long elapsedMs) {

    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        Log.v(TAG, "onVideoSizeChanged [" + " width: " + width + " height: " + height + "]");
//        resolutionTextView.setText("RES:(WxH):" + width + "X" + height + "\n           " + height + "p");//shows video info
    }


    @Override
    public void onRenderedFirstFrame(Surface surface) {

    }

    @Override
    public void onVideoDisabled(DecoderCounters counters) {

    }

    public void onPageRefresh() {
        try {
            final RxPermissions rxPermissions = new RxPermissions(this);
            checkPermission(rxPermissions);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

