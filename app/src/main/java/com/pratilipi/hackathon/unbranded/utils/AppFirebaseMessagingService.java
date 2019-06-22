package com.pratilipi.hackathon.unbranded.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.SplashActivity;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = AppFirebaseMessagingService.class.getSimpleName();
    String mNotificationChannelId = Notifications.ChannelIds.NOTIFICATION_OTHER_NOTIFICATION_CHANNEL_ID;
    private NotificationManager notifManager;

    private NotificationManager getManager(Context context) {
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifManager;
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        try {
            Log.v(TAG, "Refreshed token: " + s);
            /* If user has not logged in Or Login is not mandatory,
             * We send FCM token, saved against accesstoken, here.
             * So, that notifications can be sent to specific device.
             */
            /* added check for internet, as in function "AppUtil.uploadDataToServer(...)", we show NoConnectionDialog
             * if no internet. But, from service, we cannot show dialog. thus added check before sending request.
             */
            AppUtils.setFCMToken(getApplicationContext(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: start");
        try {
            if (remoteMessage.getData().size() > 0) {
                sendNotificationWidthOnlyDataTag(remoteMessage);

            }
        } catch (Exception e) {
            Log.v(TAG, "Error parsing FCM message" + e);
        }
    }

    private void sendNotificationWidthOnlyDataTag(RemoteMessage remoteMessage) {
        String tag = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_TAG);
        String sourceUrl = remoteMessage.getData().get(Notifications.NOTIFICATION_SOURCE_IMAGE_URL);
        String displayUrl = remoteMessage.getData().get(Notifications.NOTIFICATION_DISPLAY_IMAGE_URL);
        String title = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_TITLE);
        if (title == null)
            title = getApplicationContext().getString(R.string.app_name);
        String body = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_BODY);
        String sound = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_SOUND);
        String clickAction = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_CLICK_ACTION_BUNDLE);
        String resourceUrl = remoteMessage.getData().get(AppConstants.RESOURCE_URL);
        String notificationBundleData = remoteMessage.getData().get(AppConstants.DATA);

        //kshitij added for messages notifications.. 29 oct 18
        String sendTime = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_SEND_TIME);
        String senderId = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_SENDER_ID);
        String senderName = remoteMessage.getData().get(Notifications.NOTIFICATION_PARAM_SENDER_NAME);

        PendingIntent pendingIntent;

        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);

        android.app.Notification notification = null;
        Integer notificationId = 0;

        pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, mNotificationChannelId)
                .setSmallIcon(R.drawable.ic_def_user_circle_128)
                .setLargeIcon(getBitmap(displayUrl))
                .setColor(ContextCompat.getColor(this, R.color.red_dark2))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(pendingIntent);

        if (sound != null) {
            if (sound.equals(Notifications.DEFAULT_NOTIFICATION_SOUND)) {
                sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString();
            }
            notificationBuilder.setSound(Uri.parse(sound));
        }

        if (body != null) {
            notificationBuilder.setContentText(getHtmlString(body));
        }

        notification = notificationBuilder.build();

        try {
            showNotification(notification, clickAction, tag, notificationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showNotification(Notification notification, String clickAction,
                                  String tag, Integer notificationId) {
        try {
            notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.notify(tag, notificationId, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Spanned getHtmlString(String string) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(string);
        }
    }


    private Bitmap getBitmap(String url) {
        try {

            Drawable drawable = Glide.with(getApplicationContext())
                    .load(url)
                    .submit(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    .get();

            return ((BitmapDrawable) drawable).getBitmap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
