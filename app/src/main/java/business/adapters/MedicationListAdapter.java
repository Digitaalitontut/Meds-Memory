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

import business.DayRemindReceiver;
import business.Medication;
import business.MedicationStorage;
import business.RemindAlarm;
import business.ReminderReceiver;

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
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.med_row_text);
            button = itemView.findViewById(R.id.med_row_edit_button);
            deleteButton = itemView.findViewById(R.id.med_row_delete_button);
            button.setOnClickListener(this);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Medication med = data.get(getAdapterPosition());

                    RemindAlarm.getInstance().cancelReminder(med.getId(), DayRemindReceiver.class);
                    RemindAlarm.getInstance().cancelReminder(med.getId(), ReminderReceiver.class);

                    MedicationStorage.getInstance().delete(med);
                    setData(MedicationStorage.getInstance().getAll());
                    notifyDataSetChanged();
                }
            });
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
