package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import business.AppSettingsStorage;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_settings);

        ((Switch)findViewById(R.id.switchTheme)).setChecked(AppSettingsStorage.getInstance().get(AppSettingsStorage.Setting.DARK_MODE, false));

    }

    public void applySettings(View view) {
        Switch darkMode = findViewById(R.id.switchTheme);

        AppSettingsStorage.getInstance().set(AppSettingsStorage.Setting.DARK_MODE, darkMode.isChecked());
        AppCompatDelegate.setDefaultNightMode(darkMode.isChecked() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void editMedications(View view){
        Intent intent = new Intent(this, EditMedication.class);
        startActivity(intent);
    }
}