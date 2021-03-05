package com.example.medsmemory;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Calendar;

import business.AppSettingsStorage;
import business.Medication;
import business.MedicationStorage;
import business.RemindAlarm;

public class Application extends android.app.Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();

        AppCompatDelegate.setDefaultNightMode(AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DARK_MODE, false) ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(System.currentTimeMillis());
        start.add(Calendar.SECOND, 30);

        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(System.currentTimeMillis());
        end.add(Calendar.MINUTE, 30);

        Medication testing = new Medication();
        testing.setName("testi lääke");
        testing.setStart(start);
        testing.setEnd(end);
        testing.setTakeInterval(3);
        testing.setTakeDayInterval(1);
        testing.setDose(1.5f);
        testing.setNotes("test notes");
        MedicationStorage.getInstance().insert(testing);

        Log.d("Database: contains", MedicationStorage.getInstance().getAll().toString());

        RemindAlarm.getInstance().scheduleReminder(testing);
    }

    public static Context getAppContext() {
        return Application.context;
    }
}
