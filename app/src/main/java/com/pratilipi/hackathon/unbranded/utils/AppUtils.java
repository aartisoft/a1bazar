
package com.pratilipi.hackathon.unbranded.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.pratilipi.hackathon.unbranded.R;

public final class AppUtils {


    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static void setFCMToken(Context applicationContext, String value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        sharedPref.edit().putString(AppConstants.PREF_FCM_TOKEN, value).apply();
    }

    public static String getFCMToken(Context applicationContext) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return sharedPref.getString(AppConstants.PREF_FCM_TOKEN, null);
    }

    public static void setUserId(Context applicationContext, String value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        sharedPref.edit().putString(AppConstants.PREF_USER_ID, value).apply();
    }

    public static String getUserId(Context applicationContext) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return sharedPref.getString(AppConstants.PREF_USER_ID, null);
    }


}
