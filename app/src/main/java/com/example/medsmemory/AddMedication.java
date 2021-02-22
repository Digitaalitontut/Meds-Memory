package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class AddMedication extends AppCompatActivity {

    private TextView alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        alarm = findViewById(R.id.textViewTime);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_add);
    }

    public void setAlarm(View view ) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddMedication.this, R.style.ThemeOverlay_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
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

}

// Napit löytyy themet:
// Theme_MaterialComponents_Dialog (tumma, valkea ja turkoosi)
// ThemeOverlay_AppCompat_Dialog (turkoosi, valkea, harmaa, violetti - so far mukavin silmille)