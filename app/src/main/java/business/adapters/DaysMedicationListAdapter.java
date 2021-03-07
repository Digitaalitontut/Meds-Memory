package business.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medsmemory.R;

import java.util.ArrayList;

import business.Medication;

public class DaysMedicationListAdapter extends RecyclerView.Adapter<DaysMedicationListAdapter.ViewHolder> {
    private ArrayList<Medication> data;
    private LayoutInflater inflater;

    public DaysMedicationListAdapter(Context context, ArrayList<Medication> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Medication> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_medication_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication med = data.get(position);
        holder.text.setText(med.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.day_medication_text);
        }
    }
}
