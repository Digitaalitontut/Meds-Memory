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

/**
 * Adapter that handles showing medications for each day
 */
public class DaysMedicationListAdapter extends RecyclerView.Adapter<DaysMedicationListAdapter.ViewHolder> {
    private ArrayList<Medication> data;
    private LayoutInflater inflater;

    /**
     * Constructor for DaysMedicationListAdapter
     * @param context Application context
     * @param data Data to be used in listing
     */
    public DaysMedicationListAdapter(Context context, ArrayList<Medication> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    /**
     * Sets data. Doesn't refresh adapter.
     * @param data Data to be used in listing
     */
    public void setData(ArrayList<Medication> data) {
        this.data = data;
    }

    /**
     * Creates ViewHolder
     * @param parent parent view group
     * @param viewType view type
     * @return returns created viewholder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_medication_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Sets data to the view.
     * @param holder viewholder that holds views
     * @param position position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication med = data.get(position);
        holder.text.setText(med.getName());
    }

    /**
     * Gets size of the data
     * @return size of the data
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * ViewHolder for DaysMedicationListAdapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        /**
         * Constructor for ViewHolder
         * @param itemView List item view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.day_medication_text);
        }
    }
}
