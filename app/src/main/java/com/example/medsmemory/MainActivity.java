package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import business.MedicationStorage;
import business.WeekDay;
import business.adapters.WeekListAdapter;


public class MainActivity extends AppCompatActivity {

    WeekListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.text_appname);

        RecyclerView recyclerView = findViewById(R.id.weekView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<WeekDay> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            days.add(createWeekDay(i));
        }

        adapter = new WeekListAdapter(this,days);
        recyclerView.setAdapter(adapter);

    }

    private WeekDay createWeekDay(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, offset);
        return new WeekDay(calendar, MedicationStorage.getInstance().getAll(calendar));
    }

    public void toAddMedication(View view) {
        Intent intent = new Intent(this, AddMedication.class);
        startActivity(intent);
    }

    public void toSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}