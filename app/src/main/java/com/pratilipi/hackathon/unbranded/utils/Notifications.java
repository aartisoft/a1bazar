package com.pratilipi.hackathon.unbranded.utils;

public final class Notifications {
    //Used for firebase database and notifications
    public static final String NOTIFICATION_FIREBASE_DB_NAME = "NOTIFICATION";
    public static final String NOTIFICATION_FIREBASE_COUNT_ROW_NAME = "newNotificationCount";

    //Notification json objects
    public static final String NOTIFICATION_PARAM_LANGUAGE = "language";
    public static final String NOTIFICATION_LIST = "notificationList";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String NOTIFICATION_TIMESTAMP = "lastUpdatedMillis";
//    public static final String NOTIFICATION_STATE = StaticConstants.STATE;
    public static final String NOTIFICATION_HANDLER_TYPE = "androidHandler";
    public static final String NOTIFICATION_PRATILIPI_ID = "pratilipiId";
    public static final String NOTIFICATION_SOURCE_IMAGE_URL = "sourceImageUrl";
    public static final String NOTIFICATION_DISPLAY_IMAGE_URL = "displayImageUrl";
    public static final String NOTIFICATION_PUBLISH_JSON_OBJ = "pratilipi";
    public static final String NOTIFICATION_STATUS_UNREAD = "UNREAD";
    public static final String NOTIFICATION_STATUS_READ = "READ";
    public static final String DEFAULT_NOTIFICATION_SOUND = "default";
    public static final String FROM_SERVER_NOTIFICATION_PARAM = "FROM_SERVER_NOTIFICATION";
    public static final String NOTIFICATION_PAGE_PARAM = "notification_url_page";
    public static final String NOTIFICATION_SETTINGS = "notifications";
    public static final String NOTIFICATION_SOURCE_ID = "sourceId";
    public static final String NOTIFICATION_SECONDARY_SOURCE_ID = "dataIds"; //"secondarySourceId";
    public static final String NOTIFICATION_PARAM_TAG = "tag";
    public static final String NOTIFICATION_PARAM_TITLE = "title";
    public static final String NOTIFICATION_PARAM_BODY = "body";
    public static final String NOTIFICATION_PARAM_SOUND = "sound";
    public static final String NOTIFICATION_PARAM_CLICK_ACTION_BUNDLE = "click_action_bundle";
    public static final String NOTIFICATION_SETTING_FIREBASE_DB_NAME = "PREFERENCE";
    public static final String NOTIFICATION_SETTING_FB_EMAIL_FREQ_ROW_NAME = "emailFrequency";
    public static final String NOTIFICATION_SETTING_FB_DIGEST_FREQ_ROW_NAME = "newsletterFrequency";
    public static final String NOTIFICATION_PARAM_SEND_TIME = "sendTime";
    public static final String NOTIFICATION_PARAM_SENDER_ID = "senderId";
    public static final String NOTIFICATION_PARAM_SENDER_NAME = "senderName";
    public static final String NOTIFICATION_PARAM_SENDER_PROFILE_URL = "senderProfileUrl";
    public static final String NOTIFICATION_PARAM_SENDER_IMAGE_URL = "displayImageUrl";
    public static final String NOTIFICATION_SOURCE = "notificationSource";
    public static final String APP_SHORTCUT_NEW_CONTENT = "com.pratilipi.mobile.android.APP_SHORTCUT_NEW_CONTENT";
    public static final String APP_SHORTCUT_LIBRARY = "com.pratilipi.mobile.android.APP_SHORTCUT_LIBRARY";

    public static final String NOTIFICATION_WIDGET = "widget";

    /* IMP NOTE - any new addition / modification to list of types of notifications below,
     *  must be added in list in PratilipiFirebaseMessagingService */
    public static final class Types {
        public static final String NOTIFICATION_TYPE_PUBLISH = "NOTIFICATION_BOOK";
        public static final String NOTIFICATION_TYPE_PUBLISH_BUNDLE = "NOTIFICATION_BOOK_BUNDLE";
        public static final String NOTIFICATION_TYPE_PRATILIPI_RATING = "NOTIFICATION_NEW_RATING";
        public static final String NOTIFICATION_TYPE_REVIEW = "NOTIFICATION_NEW_REVIEW";
        public static final String NOTIFICATION_TYPE_REVIEW_COMMENT = "NOTIFICATION_NEW_COMMENT_REVIEW";
        public static final String NOTIFICATION_TYPE_COMMENT_COMMENT = "NOTIFICATION_NEW_COMMENT_COMMENT";
        public static final String NOTIFICATION_TYPE_REVIEW_LIKE = "NOTIFICATION_NEW_LIKE_REVIEW";
        public static final String NOTIFICATION_TYPE_COMMENT_LIKE = "NOTIFICATION_NEW_LIKE_COMMENT";
        public static final String NOTIFICATION_TYPE_FOLLOW = "NOTIFICATION_FOLLOWERS";
        public static final String NOTIFICATION_TYPE_FOLLOW_BUNDLE = "NOTIFICATION_FOLLOWERS_BUNDLE";
        public static final String NOTIFICATION_TYPE_AUTHOR = "NOTIFICATION_AUTHOR";
        public static final String NOTIFICATION_TYPE_EVENT = "NOTIFICATION_EVENT";
        public static final String NOTIFICATION_TYPE_CATEGORY = "NOTIFICATION_CATEGORY";
        public static final String NOTIFICATION_TYPE_APP_UPDATE = "NOTIFICATION_APPUPDATE";
        public static final String NOTIFICATION_TYPE_LIBRARY = "NOTIFICATION_LIBRARY";
        public static final String NOTIFICATION_TYPE_UPDATES = "NOTIFICATION_UPDATES";
        public static final String NOTIFICATION_TYPE_OWN_PROFILE = "NOTIFICATION_MYPROFILE";
        public static final String NOTIFICATION_TYPE_GENERIC = "NOTIFICATION_GENERIC";
        public static final String NOTIFICATION_TYPE_INCOMING_MESSAGE = "NOTIFICATION_INCOMING_MESSAGE";
        public static final String NOTIFICATION_TYPE_USER_COLLECTION = "NOTIFICATION_USER_COLLECTION";
        public static final String NOTIFICATION_TYPE_USER_COLLECTIONS = "NOTIFICATION_USER_COLLECTIONS";
    }

    public static final class GroupIds {
        public static final String NOTIFICATION_PUBLISH_GROUP_ID = "com.pratilipi.mobile.android.GROUP_PUBLISH";
        public static final String NOTIFICATION_PRATILIPI_GROUP_ID = "com.pratilipi.mobile.android.GROUP_PRATILIPI";
        public static final String NOTIFICATION_DOWNLOAD_GROUP_ID = "com.pratilipi.mobile.android.GROUP_DOWNLOAD";
        public static final String NOTIFICATION_UPDATES_GROUP_ID = "com.pratilipi.mobile.android.GROUP_UPDATES";
        public static final String NOTIFICATION_INCOMING_MESSAGE_GROUP_ID = "com.pratilipi.mobile.android.GROUP_MESSAGES";
        public static final String NOTIFICATION_USER_COLLECTION_GROUP_ID = "com.pratilipi.mobile.android.GROUP_COLLECTIONS";
    }

    public static final class ChannelIds {

        public static final String NOTIFICATION_PUBLISH_CHANNEL_ID = "com.pratilipi.mobile.android.PUBLISH";
        public static final String NOTIFICATION_DOWNLOAD_CHANNEL_ID = "com.pratilipi.mobile.android.DOWNLOAD";
        public static final String NOTIFICATION_NEW_RATING_CHANNEL_ID = "com.pratilipi.mobile.android.NEW_RATING";
        public static final String NOTIFICATION_NEW_REVIEW_CHANNEL_ID = "com.pratilipi.mobile.android.NEW_REVIEW";
        public static final String NOTIFICATION_NEW_COMMENT_CHANNEL_ID = "com.pratilipi.mobile.android.NEW_COMMENT";
        public static final String NOTIFICATION_NEW_LIKE_CHANNEL_ID = "com.pratilipi.mobile.android.NEW_LIKE";
        public static final String NOTIFICATION_NEW_FOLLOWER_CHANNEL_ID = "com.pratilipi.mobile.android.NEW_FOLLOWER";
        public static final String NOTIFICATION_WRITER_NEW_CONTENT_CHANNEL_ID = "com.pratilipi.mobile.android.WRITER_NEW_CONTENT";
        public static final String NOTIFICATION_PRATILIPI_NOTIFICATION_CHANNEL_ID = "com.pratilipi.mobile.android.PRATILIPI_NOTIFICATION";
        public static final String NOTIFICATION_INCOMING_MESSAGE_CHANNEL_ID = "com.pratilipi.mobile.android.INCOMING_MESSAGE";
        public static final String NOTIFICATION_AUDIO_PLATER_NOTIFICATION_CHANNEL_ID = "com.pratilipi.mobile.android.AUDIO_PLAYER";
        public static final String NOTIFICATION_OTHER_NOTIFICATION_CHANNEL_ID = "com.pratilipi.mobile.android.OTHER_NOTIFICATION";
        public static final String NOTIFICATION_USER_COLLECTION_CHANNEL_ID = "com.pratilipi.mobile.android.USER_COLLECTION";
    }
}
