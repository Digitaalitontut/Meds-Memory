package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * Correct title text set to the toolbar.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_appname);
    }

    /**
     * OnClick event for PLUS button.
     * Transfers user to the next activity; AddMedication
     *
     * @param view
     */
    public void toAddMedication(View view) {
        Intent intent = new Intent(this, AddMedication.class);
        startActivity(intent);
    }

    /**
     * OnClick event for "..." button.
     * Transfers user to the next activity; Settings
     *
     * @param view
     */
    public void toSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}