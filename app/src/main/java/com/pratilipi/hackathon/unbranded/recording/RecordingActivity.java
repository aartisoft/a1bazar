package com.pratilipi.hackathon.unbranded.recording;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.network.model.Product;
import com.pratilipi.hackathon.unbranded.utils.AppConstants;
import com.pratilipi.hackathon.unbranded.utils.TouchImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class RecordingActivity
        extends AppCompatActivity implements VideoRendererEventListener {


    private static final String TAG = RecordingActivity.class.getSimpleName();
    private static final int PERMISSION_CODE = 1;
    private static final int DISPLAY_WIDTH = 720;
    private static final int DISPLAY_HEIGHT = 1280;

    @BindView(R.id.selected_image)
    TouchImageView selectedImage;
    @BindView(R.id.selected_image_bg)
    AppCompatImageView selectedImageBg;

    @BindView(R.id.add_audio)
    TextView addAudio;

    @BindView(R.id.control_layout)
    LinearLayout controlLayout;

    @BindView(R.id.img_play)
    AppCompatImageView imgPlay;

    private PlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private TextView resolutionTextView;
    private int mScreenDensity;
    private MediaProjectionManager mProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private ToggleButton mToggleButton;
    private MediaRecorder mMediaRecorder;

    private Product mProduct;
    private String imageFilePath;
    private String mFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        ButterKnife.bind(this);


        imageFilePath = getIntent().getExtras().getString(AppConstants.EXTRA_IMAGE_PATH);

        mProjectionManager = (MediaProjectionManager) getSystemService
                (Context.MEDIA_PROJECTION_SERVICE);

//        mToggleButton = (ToggleButton) findViewById(R.id.toggle);
//        mToggleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onToggleScreenShare(v);
//            }
//        });

        RequestOptions options1 = new RequestOptions()
                .placeholder(R.drawable.iv_viewpagerdefault)
                .error(R.drawable.iv_viewpagerdefault)
                .priority(Priority.HIGH)
//                .fitCenter()
                .error(R.drawable.iv_viewpagerdefault)
                .placeholder(R.drawable.iv_viewpagerdefault)
                .centerCrop()
                .transform(new RoundedCornersTransformation(8, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        if (imageFilePath != null) {
            Glide.with(this)
                    .load(new File(Uri.parse(imageFilePath).getPath()))
                    .apply(options1)
                    .into(selectedImage);

            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.iv_viewpagerdefault)
                    .error(R.drawable.iv_viewpagerdefault)
                    .priority(Priority.HIGH)
                    .error(R.drawable.iv_viewpagerdefault)
                    .placeholder(R.drawable.iv_viewpagerdefault)
                    .centerCrop()
                    .transform(new BlurTransformation(10, 20))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(this)
                    .load(new File(Uri.parse(imageFilePath).getPath()))
                    .apply(options)
                    .into(selectedImageBg);
        }

//        timer.setVisibility(View.VISIBLE);
        addAudio.setVisibility(View.GONE);

        /*timer.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                timer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });

        timer.playAnimation();
*/
        addAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        controlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgPlay.getTag() == getResources().getString(R.string.play)) {
                    imgPlay.setImageResource(R.drawable.ic_stop_black_24dp);
                    imgPlay.setTag(getResources().getString(R.string.stop));
                    onToggleScreenShare(true);
                } else {
                    imgPlay.setTag(getResources().getString(R.string.play));
                    imgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    onToggleScreenShare(false);
                }
            }
        });


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;

        initRecorder();
        prepareRecorder();

        mMediaProjectionCallback = new MediaProjectionCallback();

    }


    public void showBottomSheetDialog() {


        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog, null);


        AppCompatImageView productImage = view.findViewById(R.id.product_image);
        TextView productName = view.findViewById(R.id.product_name);
        TextView productPrice = view.findViewById(R.id.product_price);
        TextView productDescription = view.findViewById(R.id.product_description);
        TextView productDescriptionHdr = view.findViewById(R.id.product_description_hdr);
        LinearLayout productBuy = view.findViewById(R.id.product_buy);

        TextView productQty = view.findViewById(R.id.product_qty);
        RelativeLayout productQtyAdd = view.findViewById(R.id.product_qty_add);
        RelativeLayout productQtyMinus = view.findViewById(R.id.product_qty_minus);

        productQtyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productQty.setText(String.valueOf(Integer.parseInt(productQty.getText().toString()) + 1));
            }
        });

        productQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(productQty.getText().toString()) > 1)
                    productQty.setText(String.valueOf(Integer.parseInt(productQty.getText().toString()) - 1));
                else
                    Toast.makeText(RecordingActivity.this, "Minimum Order 1", Toast.LENGTH_SHORT).show();
            }
        });

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.iv_viewpagerdefault)
                .error(R.drawable.iv_viewpagerdefault)
                .priority(Priority.HIGH)
//                .fitCenter()
                .error(R.drawable.iv_viewpagerdefault)
                .placeholder(R.drawable.iv_viewpagerdefault)
                .centerCrop()
                .transform(new RoundedCornersTransformation(16, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        Glide.with(this)
                .load(mProduct.getImageUrl())
                .apply(options)
                .into(productImage);

        productName.setText(mProduct.getName());
        productPrice.setText(String.format(Locale.getDefault(),
                getResources().getString(R.string.Rs) + " %d", mProduct.getPrice()));

        productDescription.setText(mProduct.getDescription());

        if (TextUtils.isEmpty(mProduct.getDescription())) {
            productDescriptionHdr.setVisibility(View.GONE);
        } else {
            productDescriptionHdr.setVisibility(View.VISIBLE);
        }

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.show();
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
//-------------------------------------------------------ANDROID LIFECYCLE---------------------------------------------------------------------------------------------

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop()...");
        if (player != null)
            player.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()...");
    }


    public void onToggleScreenShare(View view) {
        if (((ToggleButton) view).isChecked()) {
            shareScreen();
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            Log.v(TAG, "Recording Stopped");
            stopScreenSharing();
        }
    }

    public void onToggleScreenShare(boolean start) {
        if (start) {
            shareScreen();
        } else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            Log.v(TAG, "Recording Stopped");
            stopScreenSharing();
        }
    }

    private void shareScreen() {
        if (mMediaProjection == null) {
            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), PERMISSION_CODE);
            return;
        }
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    private void stopScreenSharing() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();

        try {
            Intent intent = new Intent();
            intent.putExtra(AppConstants.EXTRA_VID_PATH, mFilePath);
            setResult(RESULT_OK, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //mMediaRecorder.release();
    }

    private VirtualDisplay createVirtualDisplay() {
        return mMediaProjection.createVirtualDisplay("MainActivity",
                DISPLAY_WIDTH, DISPLAY_HEIGHT, mScreenDensity,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mMediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
    }

    private void prepareRecorder() {
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    public String getFilePath() {
        final String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "Failed to get External Storage", Toast.LENGTH_SHORT).show();
            return null;
        }
        final File folder = new File(directory);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        String filePath;
        if (success) {
            String videoName = ("capture_" + getCurSysDate() + ".mp4");
            filePath = directory + File.separator + videoName;
        } else {
            Toast.makeText(this, "Failed to create Recordings directory", Toast.LENGTH_SHORT).show();
            return null;
        }
        mFilePath = filePath;
        return filePath;
    }

    public String getCurSysDate() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    private void initRecorder() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(2048 * 1000);
            mMediaRecorder.setVideoFrameRate(30);
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
            mMediaRecorder.setOutputFile(getFilePath());
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy()...");
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }

        if (player != null)
            player.release();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PERMISSION_CODE) {
            Log.e(TAG, "Unknown request code: " + requestCode);
            return;
        }
        if (resultCode != RESULT_OK) {
            Toast.makeText(this,
                    "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();
            mToggleButton.setChecked(false);
            return;
        }
        mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
        mMediaProjection.registerCallback(mMediaProjectionCallback, null);
        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
    }

    private class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            try {
                Intent intent = new Intent();
                intent.putExtra(AppConstants.EXTRA_VID_PATH, mFilePath);
                setResult(RESULT_OK, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            if (imgPlay.getTag() == getResources().getString(R.string.play)) {
//                imgPlay.setImageResource(R.drawable.ic_stop_black_24dp);
//                imgPlay.setTag(getResources().getString(R.string.stop));
//            }
////            mToggleButton.setChecked(false);
//            mMediaRecorder.stop();
//            mMediaRecorder.reset();
//            Log.v(TAG, "Recording Stopped");
//            mMediaProjection = null;
//            stopScreenSharing();
            Log.i(TAG, "MediaProjection Stopped");
        }
    }

}
