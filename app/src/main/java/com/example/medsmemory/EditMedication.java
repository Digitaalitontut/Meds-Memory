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
    public  static final String EXTRA_TITLE = "ADDMEDICATION";
    // ListViewiin kaikki lisätyt lääkkeet? Lääkkeen nimeä klikatessa avautuu...esitäytetty
    // AddMedication?
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
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedicationListAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setData(MedicationStorage.getInstance().getAll());
        adapter.notifyDataSetChanged();
    }

    /**
     * OnClick event for the ListView elements/edit button????
     * Transfers user to AddMedication activity and autofills it with previously stored information.
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        String text = "Yes";
        Intent intent = new Intent(this, AddMedication.class);
        intent.putExtra(EXTRA_MEDICATION_ID, data.get(position).getId());
        intent.putExtra(EXTRA_TITLE, text);
        startActivity(intent);
    }
}