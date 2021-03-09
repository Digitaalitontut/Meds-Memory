package business;

import androidx.annotation.NonNull;

import java.util.Calendar;

/**
 * Model for medication
 */
public class Medication {

    private long id;
    private String name;
    private Calendar start;
    private Calendar end;

    private int takeDayInterval; // take every x days
    private int takeInterval; // take x amount of times in a day

    private float dose;
    private String notes;

    public Medication() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public float getDose() {
        return dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("id: %s, name: %s", id, name);
    }

    public int getTakeDayInterval() {
        return takeDayInterval;
    }

    public void setTakeDayInterval(int takeDayInterval) {
        this.takeDayInterval = takeDayInterval;
    }

    public int getTakeInterval() {
        return takeInterval;
    }

    public void setTakeInterval(int takeInterval) {
        this.takeInterval = takeInterval;
    }
}
