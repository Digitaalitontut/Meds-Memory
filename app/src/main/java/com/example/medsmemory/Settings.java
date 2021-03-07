package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import business.AppSettingsStorage;

public class Settings extends AppCompatActivity {

    private EditText start;
    private EditText end;

    /**
     * Correct title text set to the toolbar.
     * Checks whether DarkMode is set on or not.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_settings);

        start = findViewById(R.id.editTextDayStart);
        end = findViewById(R.id.editTextDayEnd);

        ((Switch) findViewById(R.id.switchTheme)).setChecked(AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DARK_MODE, false));

    }

    /**
     * OnClick event for "Edit medications" button.
     * Transfers user to next activity; EditMedications
     *
     * @param view
     */
    public void editMedications(View view) {
        Intent intent = new Intent(this, EditMedication.class);
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void applySettings(View view) {
        Switch darkMode = findViewById(R.id.switchTheme);

        AppSettingsStorage.getInstance().set(AppSettingsStorage.Setting.DARK_MODE,
                darkMode.isChecked());
        AppCompatDelegate.setDefaultNightMode(darkMode.isChecked() ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    /**
     * Creates a time picker when one of the clock icon is clicked.
     * Picked time will be sent to an EditText according to which button is pressed.
     *
     * @param view
     */
    public void setTime(View view) {
        EditText text;
        if (view.getId() == R.id.buttonDayStart) {
            text = this.start;
        } else {
            text = this.end;
        }

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                R.style.ThemeOverlay_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int min) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, min);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("k:mm");
                String set = format.format(c.getTime());
                text.setText(set);
            }

        }, hour, min, true);
        timePickerDialog.show();
    }
}