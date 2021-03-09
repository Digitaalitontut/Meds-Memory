package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import business.Medication;
import business.MedicationStorage;
import business.NotificationService;
import business.RemindAlarm;

/**
 * Screen shown during alarm. Dismiss by checking medicine taken.
 */
public class Reminder extends AppCompatActivity {


    Medication medication;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        long id = intent.getLongExtra(RemindAlarm.EXTRA_NOTIFICATION_KEY, -1);
        medication = MedicationStorage.getInstance().get(id);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_reminder);
        TextView dose = findViewById(R.id.textViewDose);
        TextView medicine = findViewById(R.id.textViewMedicine);
        TextView notes = findViewById(R.id.textViewNotes);

        dose.setText(Float.toString(medication.getDose()) + " pills of");
        medicine.setText(medication.getName());
        notes.setText(medication.getNotes());
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
        Intent intentService = new Intent(getApplicationContext(), NotificationService.class);
        getApplicationContext().stopService(intentService);
        MedicationStorage.getInstance().insertLog(medication, Calendar.getInstance());
        finish();
    }
}