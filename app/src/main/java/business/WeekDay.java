package business;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekDay {
    private Calendar date;
    private ArrayList<Medication> medications;

    public WeekDay(Calendar date, ArrayList<Medication> medications) {
        this.date = date;
        this.medications = medications;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }

    public void setMedications(ArrayList<Medication> medications) {
        this.medications = medications;
    }
}
