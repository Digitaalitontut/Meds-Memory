package com.example.medsmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import business.Medication;
import business.adapters.MedicationListAdapter;
import business.MedicationStorage;
import business.adapters.RecyclerClickListener;

public class EditMedication extends AppCompatActivity implements RecyclerClickListener {

    public static final String EXTRA_MEDICATION_ID = "EXTRA_MED_ID";
    // ListViewiin kaikki lisätyt lääkkeet? Lääkkeen nimeä klikatessa avautuu...esitäytetty AddMedication?
    // Joka tapauksessa vie jonkinlaiseen aktiviteettiin, jossa lääkitystä voi muokata tai poistaa.

    MedicationListAdapter adapter;

    List<Medication> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(R.string.button_edit);

        data = MedicationStorage.getInstance().getAll();

        RecyclerView recyclerView = findViewById(R.id.medicationList);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedicationListAdapter(this,data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
            Intent intent = new Intent(this, AddMedication.class);
            intent.putExtra(EXTRA_MEDICATION_ID, data.get(position).getId());
            startActivity(intent);
    }
}