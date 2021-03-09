package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import business.AppSettingsStorage;

/**
 * Set notifications and dark mode ON/OFF. Set personal day cycle. Go to list of your medications.
 */
public class Settings extends AppCompatActivity {

    private EditText start;
    private EditText end;

    private HashMap<Integer, Calendar> calendarHashMap = new HashMap<>();
    private SimpleDateFormat format = new SimpleDateFormat("k:mm");

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

        Calendar startCalendar = Calendar.getInstance();
        long dayStartSetting =
                AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DAY_START, 0L);
        if (dayStartSetting > 0) {
            startCalendar.setTimeInMillis(dayStartSetting);
        } else {
            startCalendar.set(Calendar.HOUR, 9);
            startCalendar.set(Calendar.MINUTE, 0);
            startCalendar.set(Calendar.SECOND, 0);
            startCalendar.set(Calendar.MILLISECOND, 0);
            AppSettingsStorage.getInstance().set(AppSettingsStorage.Setting.DAY_START,
                    startCalendar.getTimeInMillis());
        }

        calendarHashMap.put(start.getId(), startCalendar);
        start.setText(format.format(startCalendar.getTime()));

        Calendar endCalendar = Calendar.getInstance();
        long dayEndSetting =
                AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DAY_END, 0L);
        if (dayEndSetting > 0) {
            endCalendar.setTimeInMillis(dayEndSetting);
        } else {
            endCalendar.set(Calendar.HOUR, 21);
            endCalendar.set(Calendar.MINUTE, 0);
            endCalendar.set(Calendar.SECOND, 0);
            endCalendar.set(Calendar.MILLISECOND, 0);
            AppSettingsStorage.getInstance().set(AppSettingsStorage.Setting.DAY_END,
                    endCalendar.getTimeInMillis());
        }

        calendarHashMap.put(end.getId(), endCalendar);
        end.setText(format.format(endCalendar.getTime()));
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
     * OnClick event for dark mode Switch and Apply button.
     * Changes theme according to the position of the switch.
     * Tallentaa asetetun päivärytmin.
     *
     * @param view
     */
    public void applySettings(View view) {
        Switch darkMode = findViewById(R.id.switchTheme);
        AppSettingsStorage settings = AppSettingsStorage.getInstance();

        settings.set(AppSettingsStorage.Setting.DARK_MODE,
                darkMode.isChecked());
        AppCompatDelegate.setDefaultNightMode(darkMode.isChecked() ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        settings.set(AppSettingsStorage.Setting.DAY_START,
                calendarHashMap.get(R.id.editTextDayStart).getTimeInMillis());
        settings.set(AppSettingsStorage.Setting.DAY_END,
                calendarHashMap.get(R.id.editTextDayEnd).getTimeInMillis());
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
        try {
            Date d = format.parse(text.getText().toString());
            calendar.setTime(d);
        } catch (Exception e) {
            Log.e("Parse error: ", "Error parsing date", e);
        }

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
                calendarHashMap.put(text.getId(), c);
                String set = format.format(c.getTime());
                text.setText(set);
            }

        }, hour, min, true);
        timePickerDialog.show();
    }
}