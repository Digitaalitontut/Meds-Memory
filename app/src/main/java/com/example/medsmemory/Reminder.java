package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;

import java.util.Calendar;

import business.RemindAlarm;

public class Reminder extends AppCompatActivity {

    // TODO: Noutaa lääkityksen tiedot niille varattuihin tekstikenttiin.

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_reminder);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * OnClick event for the CheckBox.
     * Closes the Reminder activity.
     * Should mark the medicine as taken.
     *
     * @param view
     */
    public void onCheck(View view) {
        // TODO: Kalenteriin lääkkeen merkintä otetuksi
        Intent intentService = new Intent(getApplicationContext(), Application.class);
        getApplicationContext().stopService(intentService);
        finish();
    }

    /*
     * OnClick event for snooze button.
     * Creates new notification scheduled to 10 minutes away from now.
     * Closes current activity.
     *
     * @param view

    public void snooze(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 10);
        // TODO: Tähän pitää keksiä ratkaisu
       // RemindAlarm.getInstance().scheduleNotification(calendar);

        Intent intentService = new Intent(getApplicationContext(), Application.class);
        getApplicationContext().stopService(intentService);
        finish();
    }*/
}