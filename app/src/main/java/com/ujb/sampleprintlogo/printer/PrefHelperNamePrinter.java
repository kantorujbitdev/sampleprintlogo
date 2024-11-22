package com.ujb.sampleprintlogo.printer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefHelperNamePrinter {

    private static PrefHelperNamePrinter sInstance;

    public static String NAME_WOOSIM = "NAME_WOOSIM";
    private final SharedPreferences mPreferences;

    public static void initDefault(Context context) {
        sInstance = new PrefHelperNamePrinter(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public static PrefHelperNamePrinter getDefault() {
        return sInstance;
    }

    public static PrefHelperNamePrinter get(Context context, String name) {
        return new PrefHelperNamePrinter(context, name);
    }

    private PrefHelperNamePrinter(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    private PrefHelperNamePrinter(Context context, String name) {
        mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor edit() {
        return mPreferences.edit();
    }

    public SharedPreferences.Editor putInt(String key, int value) {
        return edit().putInt(key, value);
    }
    public SharedPreferences.Editor putFloat(String key, float value) {
        return edit().putFloat(key, value);
    }

    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return edit().putBoolean(key, value);
    }

    public void saveBoolean(String key, boolean value) {
        putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public SharedPreferences.Editor putString(String key, String value) {
        return edit().putString(key, value);
    }

    public void saveString(String key, String value) {
        putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }
}
