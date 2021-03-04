package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddMedication extends AppCompatActivity {

    private TextView alarm;
    private EditText until;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        alarm = findViewById(R.id.textViewTime);
        until = findViewById(R.id.editTextDate);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_add);
    }

    // Creates a time picker when clock icon is clicked. Picked time then will be sent to TextView.
    public void setAlarm(View view) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.ThemeOverlay_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("k:mm");
                String time = format.format(c.getTime());
                alarm.setText(time);
            }
        }, hour, min, true);
        timePickerDialog.show();
    }

    // Creates a date picker when calendar icon is clicked. Picked date will be sent to editText next to it.
    public void setDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.ThemeOverlay_AppCompat_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String set = format.format(c.getTime());
                until.setText(set);
            }

        }, year, month, day);
        datePickerDialog.show();
    }
}

// Napit löytyy themet:
// Theme_MaterialComponents_Dialog (tumma, valkea ja turkoosi)
// ThemeOverlay_AppCompat_Dialog (turkoosi, valkea, harmaa, violetti - so far mukavin silmille)