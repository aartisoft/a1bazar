package com.pratilipi.hackathon.unbranded.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;


public class NotificationChannelHelper {
    private static final String TAG = NotificationChannelHelper.class.getSimpleName();

    private static NotificationManager notifManager;
    private static String groupPublish, groupPratilipi, groupDownload, groupUpdates, groupMessages, groupOther, groupAudioPlayer, groupUserCollection;

    public NotificationChannelHelper(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels(context);
        }
    }

    public NotificationChannelHelper(Context context, String notificationChannelId, int type) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createGroups(context);
//            createChannel(context, notificationChannelId, type);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createGroups(Context context) {

        groupOther = Notifications.ChannelIds.NOTIFICATION_OTHER_NOTIFICATION_CHANNEL_ID;
        CharSequence groupOtherName = "Notifications";

        getManager(context).createNotificationChannelGroup(new NotificationChannelGroup(groupOther, groupOtherName));

    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void createChannels(Context context) {

        createGroups(context);

        NotificationChannel otherNotificationsChannel =
                new NotificationChannel(Notifications.ChannelIds.NOTIFICATION_OTHER_NOTIFICATION_CHANNEL_ID,
                        "Notifications", NotificationManager.IMPORTANCE_DEFAULT);
        otherNotificationsChannel.enableLights(true);
        otherNotificationsChannel.enableVibration(true);
        otherNotificationsChannel.setLightColor(Color.RED);
        otherNotificationsChannel.setShowBadge(true);
        otherNotificationsChannel.setGroup(groupOther);
        otherNotificationsChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager(context).createNotificationChannel(otherNotificationsChannel);
    }

    private static NotificationManager getManager(Context context) {
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void getChannels(Context context) {

        Log.d(TAG, "NotificationManagerCompat.from(context).areNotificationsEnabled(): " +
                NotificationManagerCompat.from(context).areNotificationsEnabled());

        NotificationChannel downloadChannel = notifManager.getNotificationChannel(Notifications.ChannelIds.NOTIFICATION_DOWNLOAD_CHANNEL_ID);
        Log.d(TAG, Notifications.ChannelIds.NOTIFICATION_DOWNLOAD_CHANNEL_ID + "downloadChannel.getImportance: " + downloadChannel.getImportance());

        NotificationChannel newRatingChannel = notifManager.getNotificationChannel(Notifications.ChannelIds.NOTIFICATION_NEW_RATING_CHANNEL_ID);
        Log.d(TAG, Notifications.ChannelIds.NOTIFICATION_DOWNLOAD_CHANNEL_ID + "newRatingChannel.getImportance: " + newRatingChannel.getImportance());

    }

}
