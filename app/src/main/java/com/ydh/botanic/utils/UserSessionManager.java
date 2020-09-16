package com.ydh.botanic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserSessionManager {
    static final String REGISTERED_KEY_USER ="user";
    static final String LOGGED_IN_KEY_EMAIL ="Email_logged_in";
    static final String  LOGGED_IN_KEY_USERNAME= "Username_logged_in";
    static final String  LOGGED_IN_KEY_STATUS = "Status_logged_in";
    static final String LOGGED_IN_KEY_TOKEN = "Token_logged_in";
    static final String LANGUAGE_APP = "Language_app";


    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setRegisteredUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(REGISTERED_KEY_USER, username);
        editor.apply();
    }

    public static String getRegisteredUser(Context context){
        return getSharedPreference(context).getString(REGISTERED_KEY_USER,"");
    }

    public static String getLanguageApp(Context context){
        return getSharedPreference(context).getString(LANGUAGE_APP,"");
    }

    public static void setLanguageApp(Context context, String language){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LANGUAGE_APP, language);
        editor.apply();
    }

    public static String getLoggedInKeyToken(Context context){
        return getSharedPreference(context).getString(LOGGED_IN_KEY_TOKEN, "");
    }

    public static void setLoggedInKeyToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LOGGED_IN_KEY_TOKEN, token);
        editor.apply();
    }

//    public static void setRegisteredPass(Context context, String password){
//        SharedPreferences.Editor editor = getSharedPreference(context).edit();
//        editor.putString(REGISTERED_KEY_PASS, password);
//        editor.apply();
//    }
//
//    public static String getRegisteredPass(Context context){
//        return getSharedPreference(context).getString(REGISTERED_KEY_PASS,"");
//    }

    public static void setLoggedInKeyEmail(Context context, String email){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LOGGED_IN_KEY_EMAIL, email);
        editor.apply();
    }
    public static String getLoggedInKeyEmail(Context context){
        return getSharedPreference(context).getString(LOGGED_IN_KEY_EMAIL,"");
    }


    public static void setLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(LOGGED_IN_KEY_USERNAME, username);
        editor.apply();
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(LOGGED_IN_KEY_USERNAME,"");
    }

    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(LOGGED_IN_KEY_STATUS,status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(LOGGED_IN_KEY_STATUS,false);
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(LOGGED_IN_KEY_USERNAME);
        editor.remove(LOGGED_IN_KEY_EMAIL);
        editor.remove(LOGGED_IN_KEY_TOKEN);
        editor.remove(LOGGED_IN_KEY_STATUS);
        editor.apply();
    }
}
