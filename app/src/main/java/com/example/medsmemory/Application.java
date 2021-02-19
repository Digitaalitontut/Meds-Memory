package com.example.medsmemory;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import business.AppSettingsStorage;

public class Application extends android.app.Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();

        AppCompatDelegate.setDefaultNightMode(AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DARK_MODE, false) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

    }

    public static Context getAppContext() {
        return Application.context;
    }
}
