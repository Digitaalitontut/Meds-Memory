package business.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medsmemory.Application;
import com.example.medsmemory.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import business.WeekDay;

public class WeekListAdapter extends RecyclerView.Adapter<WeekListAdapter.ViewHolder> {
    private ArrayList<WeekDay> data;
    private LayoutInflater inflater;
    private RecyclerClickListener clickListener;

    public WeekListAdapter(Context context, ArrayList<WeekDay> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.week_day_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeekDay day = data.get(position);
        Log.d("Day:" + position, day.getMedications().toString());
        holder.dayText.setText(day.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        holder.dayNumeric.setText(new SimpleDateFormat("dd.MM.yyyy").format(day.getDate().getTime()));
        holder.adapter.setData(day.getMedications());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dayText;
        TextView dayNumeric;
        RecyclerView recyclerView;
        DaysMedicationListAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.week_date_text);
            dayNumeric = itemView.findViewById(R.id.week_date_numberic);
            recyclerView = itemView.findViewById(R.id.day_medications);
            recyclerView.setLayoutManager(new LinearLayoutManager(Application.getAppContext()));
            adapter = new DaysMedicationListAdapter(Application.getAppContext(), new ArrayList<>());
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
