package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import business.Medication;
import business.MedicationStorage;

public class AddMedication extends AppCompatActivity {

    private EditText from;
    private EditText until;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        from = findViewById(R.id.editTextStart);
        until = findViewById(R.id.editTextEnd);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_add);

        Intent intent = getIntent();
        long id = intent.getLongExtra(EditMedication.EXTRA_MEDICATION_ID, -1);
        if(id > -1) {
            Medication med = MedicationStorage.getInstance().get(id);
            EditText name = findViewById(R.id.editTextMedication);
            name.setText(med.getName());
        }
    }

    public void addMedication(View view) {
        Medication med = new Medication();
        med.setName(((EditText)findViewById(R.id.editTextMedication)).getText().toString());
        med.setDose(Float.valueOf(((EditText)findViewById(R.id.editTextMedication)).getText().toString()));

    }


    /**
     * Creates a date picker when one of the calendar icon is clicked.
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.ThemeOverlay_AppCompat_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String set = format.format(c.getTime());
                text.setText(set);
            }

        }, year, month, day);
        datePickerDialog.show();
    }
}

// Napit löytyy themet:
// Theme_MaterialComponents_Dialog (tumma, valkea ja turkoosi)
// ThemeOverlay_AppCompat_Dialog (turkoosi, valkea, harmaa, violetti - so far mukavin silmille)