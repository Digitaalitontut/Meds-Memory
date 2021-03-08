package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import business.DayRemindReceiver;
import business.Medication;
import business.MedicationStorage;
import business.RemindAlarm;
import business.ReminderReceiver;

public class AddMedication extends AppCompatActivity {

    private EditText name;
    private EditText dose;
    private EditText from;
    private EditText until;
    private EditText takeInterval;
    private EditText takeDayInterval;
    private EditText notes;

    private Medication med;

    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        name = findViewById(R.id.editTextMedication);
        dose = findViewById(R.id.editTextDose);

        from = findViewById(R.id.editTextStart);
        until = findViewById(R.id.editTextEnd);

        takeDayInterval = findViewById(R.id.editTextDays);
        takeInterval = findViewById(R.id.editTextNumber);
        notes = findViewById(R.id.editMultiLineNotes);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_add);

        Intent intent = getIntent();
        String text = intent.getStringExtra(EditMedication.EXTRA_TITLE);
        if (text != null){
            title.setText(R.string.button_edit);
        }

        long id = intent.getLongExtra(EditMedication.EXTRA_MEDICATION_ID, -1);
        if (id > -1) {
            med = MedicationStorage.getInstance().get(id);
            try {
                name.setText(med.getName());
                dose.setText(Float.toString(med.getDose()));
                from.setText(format.format(med.getStart().getTime()));
                calendarHashMap.put(R.id.editTextStart, med.getStart());
                until.setText(format.format(med.getEnd().getTime()));
                calendarHashMap.put(R.id.editTextEnd, med.getEnd());
                takeDayInterval.setText(Integer.toString(med.getTakeDayInterval()));
                takeInterval.setText(Integer.toString(med.getTakeInterval()));
                notes.setText(med.getNotes());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
            }
        } else {
            med = new Medication();
        }
    }

    /**
     * OnClick event for Submit button.
     *
     * @param view
     */
    public void addMedication(View view) {
        med.setName(((EditText) findViewById(R.id.editTextMedication)).getText().toString());
        med.setDose(Float.valueOf(((EditText) findViewById(R.id.editTextDose)).getText().toString()));
        med.setStart(calendarHashMap.get(R.id.editTextStart));
        med.setEnd(calendarHashMap.get(R.id.editTextEnd));
        med.setTakeDayInterval(Integer.valueOf(takeDayInterval.getText().toString()));
        med.setTakeInterval(Integer.valueOf(takeInterval.getText().toString()));
        med.setNotes(notes.getText().toString());

        if (med.getId() == 0) {
            MedicationStorage.getInstance().insert(med);
        } else {
            MedicationStorage.getInstance().update(med);
            RemindAlarm.getInstance().cancelReminder(med.getId(), DayRemindReceiver.class);
            RemindAlarm.getInstance().cancelReminder(med.getId(), ReminderReceiver.class);
        }
        RemindAlarm.getInstance().scheduleReminder(med);
        finish();
    }

    HashMap<Integer, Calendar> calendarHashMap = new HashMap<>();

    /**
     * Creates a date picker when one of the calendar icons is clicked.
     * Picked date will be sent to an EditText according to which button is pressed.
     *
     * @param view
     */
    public void setDate(View view) {
        EditText text;
        if (view.getId() == R.id.buttonStart) {
            text = this.from;
        } else {
            text = this.until;
        }

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                R.style.ThemeOverlay_AppCompat_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                String set = format.format(c.getTime());
                text.setText(set);
                calendarHashMap.put(text.getId(), c);
            }

        }, year, month, day);
        datePickerDialog.show();
    }
}

// Napit löytyy themet:
// Theme_MaterialComponents_Dialog (tumma, valkea ja turkoosi)
// ThemeOverlay_AppCompat_Dialog (turkoosi, valkea, harmaa, violetti - so far mukavin silmille)