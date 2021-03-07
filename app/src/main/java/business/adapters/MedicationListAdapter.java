package business.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medsmemory.R;

import java.util.ArrayList;
import java.util.List;

import business.Medication;

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.ViewHolder> {
    private List<Medication> data;
    private LayoutInflater inflater;
    private RecyclerClickListener clickListener;

    public MedicationListAdapter(Context context, List<Medication> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<Medication> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.medication_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medication med = data.get(position);
        holder.text.setText(med.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.med_row_text);
            button = itemView.findViewById(R.id.med_row_edit_button);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null)clickListener.onItemClick(v,getAdapterPosition());
        }
    }

    public void setClickListener(RecyclerClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
