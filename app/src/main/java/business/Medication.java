package business;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Medication {

    private long id;
    private String name;
    private Calendar start;
    private Calendar end;
    private long intervalInMilliseconds;

    private float dose;
    private String notes;

    public Medication(String name, Calendar start, Calendar end, long intervalInMilliseconds, float dose, String notes) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.intervalInMilliseconds = intervalInMilliseconds;
        this.dose = dose;
        this.notes = notes;
    }

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

    public long getIntervalInMilliseconds() {
        return intervalInMilliseconds;
    }

    public void setIntervalInMilliseconds(long intervalInMilliseconds) {
        this.intervalInMilliseconds = intervalInMilliseconds;
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
}
