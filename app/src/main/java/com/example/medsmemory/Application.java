package com.example.medsmemory;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Calendar;

import business.AppSettingsStorage;
import business.Medication;
import business.MedicationStorage;
import business.RemindAlarm;

/**
 * Main Application
 */
public class Application extends android.app.Application {
    private static Context context;

    /**
     * Initializes context and sets night mode
     */
    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();

        AppCompatDelegate.setDefaultNightMode(AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DARK_MODE, false) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * returns Application context
     * @return Application context
     */
    public static Context getAppContext() {
        return Application.context;
    }
}
