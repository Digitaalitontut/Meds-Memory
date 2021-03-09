package business;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Model for week day that will be used in front page calendar
 */
public class WeekDay {
    private Calendar date;
    private ArrayList<Medication> medications;

    /**
     * Constructor for WeekDay
     * @param date date of the weekday
     * @param medications Medications for this day
     */
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
