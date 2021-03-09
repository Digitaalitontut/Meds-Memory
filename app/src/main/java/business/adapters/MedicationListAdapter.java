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

/**
 * Adapter that handles showing medications in EditMedication activity
 */
public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.ViewHolder> {
    private List<Medication> data;
    private LayoutInflater inflater;
    private RecyclerClickListener clickListener;

    /**
     * Constructor for MedicationListAdapter
     * @param context Application context
     * @param data Data to be used in listing
     */
    public MedicationListAdapter(Context context, List<Medication> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    /**
     * Sets data to be used. Doesn't refresh adapter.
     * @param data Adapter data
     */
    public void setData(ArrayList<Medication> data) {
        this.data = data;
    }

    /**
     * Creates ViewHolder
     * @param parent parent
     * @param viewType viewType
     * @return returns created ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.medication_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Sets data to the view
     * @param holder ViewHolder
     * @param position position where to read data
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medication med = data.get(position);
        holder.text.setText(med.getName());
    }

    /**
     * Gets size of the data
     * @return returns size of the data
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * ViewHolder for MedicationListAdapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        Button button;
        Button deleteButton;

        /**
         * Constructor for ViewHolder
         * @param itemView List item view
         */
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

        /**
         * Handles Edit button click
         * @param v Button that was clicked
         */
        @Override
        public void onClick(View v) {
            if(clickListener != null)clickListener.onItemClick(v,getAdapterPosition());
        }
    }

    /**
     * Sets click listener for edit button
     * @param itemClickListener Class that implements RecyclerClickListener
     */
    public void setClickListener(RecyclerClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}
