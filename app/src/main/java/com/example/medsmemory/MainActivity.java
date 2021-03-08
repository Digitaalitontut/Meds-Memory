package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import business.MedicationStorage;
import business.WeekDay;
import business.adapters.WeekListAdapter;

/**
 * View of calendar. Buttons to AddMedication and Settings.
 */
public class MainActivity extends AppCompatActivity {

    WeekListAdapter adapter;

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

        RecyclerView recyclerView = findViewById(R.id.weekView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<WeekDay> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days.add(createWeekDay(i));
        }

        adapter = new WeekListAdapter(this, days);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        ArrayList<WeekDay> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days.add(createWeekDay(i));
        }
        adapter.setData(days);
        adapter.notifyDataSetChanged();
    }

    private WeekDay createWeekDay(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, offset);
        return new WeekDay(calendar, MedicationStorage.getInstance().getAll(calendar));
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