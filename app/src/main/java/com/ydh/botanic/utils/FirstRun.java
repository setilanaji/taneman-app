package com.ydh.botanic.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FirstRun {
    static final String FIRST_RUN = "first";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void setFirstRun(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(FIRST_RUN, status);
        editor.apply();
    }

    public static boolean getFirstRun(Context context){
        return getSharedPreference(context).getBoolean(FIRST_RUN, true);
    }

    public static void clearFirstRun (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(FIRST_RUN);
        editor.apply();
    }

}
