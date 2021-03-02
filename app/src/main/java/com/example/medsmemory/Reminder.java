package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

import business.RemindAlarm;
import business.ReminderPublisher;

public class Reminder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
    }

    public void onCheck(View view) {
        // TODO: Kalenteriin lääkkeen merkintä otetuksi
        Intent intentService = new Intent(getApplicationContext(), Application.class);
        getApplicationContext().stopService(intentService);
        finish();
    }

    public void snooze(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 10);

        RemindAlarm.getInstance().scheduleNotification(calendar);

        Intent intentService = new Intent(getApplicationContext(), Application.class);
        getApplicationContext().stopService(intentService);
        finish();
    }
}