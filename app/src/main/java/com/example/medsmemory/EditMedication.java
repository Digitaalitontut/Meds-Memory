package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class EditMedication extends AppCompatActivity {

    // ListViewiin kaikki lisätyt lääkkeet? Lääkkeen nimeä klikatessa avautuu...esitäytetty AddMedication?
    // Joka tapauksessa vie jonkinlaiseen aktiviteettiin, jossa lääkitystä voi muokata tai poistaa.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.button_edit);
    }
}