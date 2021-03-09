package business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.medsmemory.Application;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import business.data.MedicationReaderContract;
import business.data.MedicationReaderDbHelper;

/**
 * Storage for medications. Handles saving and reading medications from database
 */
public class MedicationStorage {

    private static MedicationStorage _instance = null;
    private MedicationReaderDbHelper dbHelper;

    private MedicationStorage(){
        dbHelper = new MedicationReaderDbHelper(Application.getAppContext());
    }

    /**
     * Creates MedicationStorage if it doesn't exist. Returns MedicationStorage instance
     * @return MedicationStorage singleton
     */
    public static MedicationStorage getInstance() {
        if(_instance == null) _instance = new MedicationStorage();
        return _instance;
    }

    /**
     * Inserts medication to database
     * @param med medication that will be inserted to database
     * @return returns medication that has correct id
     */
    public Medication insert(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        med.setId(db.insert(MedicationReaderContract.MedicationEntry.TABLE_NAME,null, medicationToContentValues(med)));
        return med;
    }

    /**
     * Maps Medication to ContentValues
     * @param med Medication to map
     * @return returns mapped ContentValues
     */
    private ContentValues medicationToContentValues(Medication med) {
        ContentValues values = new ContentValues();
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME, med.getName());
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_START, med.getStart().getTimeInMillis());
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_END, med.getEnd().getTimeInMillis());
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_TAKEDAYINTERVAL, med.getTakeDayInterval());
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_TAKEINTERVAL, med.getTakeInterval());
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_DOSE, med.getDose());
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NOTES, med.getNotes());
        return values;
    }

    /**
     * Updates Medication information in the database
     * @param med Medication to be updated
     * @return returns affected rows
     */
    public int update(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(
                MedicationReaderContract.MedicationEntry.TABLE_NAME,
                medicationToContentValues(med),
                MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID + "=?",
                new String[]{Long.toString(med.getId())}
        );
    }

    /**
     * Deletes medication from database
     * @param med Medication to be removed
     * @return returns affected rows
     */
    public int delete(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(MedicationReaderContract.MedicationEntry.TABLE_NAME, MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID+"=?", new String[]{Long.toString(med.getId())});
    }

    /**
     * Gets medication from the database
     * @param id id of the medication
     * @return returns medication
     */
    public Medication get(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MedicationReaderContract.MedicationEntry.TABLE_NAME + " WHERE " + MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID + " = " + Long.toString(id), null);
        if(c != null) c.moveToFirst();
        return medicationFromCursor(c);
     }

    /**
     * Maps Medication from cursor
     * @param cursor cursor that contains the data
     * @return returns mapped medication
     */
     private Medication medicationFromCursor(Cursor cursor) {
         Calendar start = Calendar.getInstance();
         start.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_START)));
         Calendar end = Calendar.getInstance();
         end.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_END)));
         Medication med = new Medication();
         med.setId(cursor.getInt(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID)));
         med.setName(cursor.getString(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME)));
         med.setStart(start);
         med.setEnd(end);
         med.setTakeDayInterval(cursor.getInt(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_TAKEDAYINTERVAL)));
         med.setTakeInterval(cursor.getInt(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_TAKEINTERVAL)));
         med.setDose(cursor.getInt(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_DOSE)));
         med.setNotes(cursor.getString(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NOTES)));
         return med;
     }

    /**
     * Inserts log entry to that database
     * @param med Medication
     * @param takenAt Date when that medication was taken as Calendar object
     */
     public void insertLog(Medication med, Calendar takenAt) {
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(MedicationReaderContract.MedicationLog.COLUMN_NAME_MED_ID, med.getId());
         values.put(MedicationReaderContract.MedicationLog.COLUMN_NAME_TAKEN_AT, takenAt.getTimeInMillis());
         db.insert(MedicationReaderContract.MedicationLog.TABLE_NAME,null, values);
     }

    /**
     * Deletes Logs
     * @param med Medication
     * @return returns affected rows
     */
     public int deleteLog(Medication med) {
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         return db.delete(MedicationReaderContract.MedicationLog.TABLE_NAME, MedicationReaderContract.MedicationLog.COLUMN_NAME_MED_ID+"=?", new String[]{Long.toString(med.getId())});
     }

    /**
     * Counts log entries inside specific time span
     * @param med Medication
     * @param start Start of the span
     * @param end end of the span
     * @return returns the count
     */
     public long countLog(Medication med, Calendar start, Calendar end) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, MedicationReaderContract.MedicationLog.TABLE_NAME, MedicationReaderContract.MedicationLog.COLUMN_NAME_MED_ID + " = ? AND " +
                MedicationReaderContract.MedicationLog.COLUMN_NAME_TAKEN_AT + " > ? AND " +
                MedicationReaderContract.MedicationLog.COLUMN_NAME_TAKEN_AT + " < ?",
                new String[]{Long.toString(med.getId()), Long.toString(med.getStart().getTimeInMillis()), Long.toString(med.getEnd().getTimeInMillis())}
        );
     }

    /**
     * Gets all medication from database
     * @return returns medications
     */
    public ArrayList<Medication> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = null;
        String[] selectionArgs = null;
        Cursor cursor = db.query(
                MedicationReaderContract.MedicationEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        ArrayList<Medication> meds = new ArrayList<>();

        while(cursor.moveToNext()) {
            meds.add(medicationFromCursor(cursor));
        }
        cursor.close();

        return meds;
    }

    /**
     * Gets medications that should be taken at specific day
     * @param day Day the the medication should be taken
     * @return return medications
     */
    // TODO this is dirty way of doing this. Don't have time to think better solution
    public ArrayList<Medication> getAll(Calendar day) {
        ArrayList<Medication> meds = getAll();
        Calendar start = atDayStart(day);
        Calendar end = atDayEnd(day);
        ArrayList<Medication> includedMeds = new ArrayList<>();
        for(Medication med : meds) {
            for(int i = 0; true; i++) {
                long notificationTime = med.getStart().getTimeInMillis() + TimeUnit.DAYS.toMillis(i*med.getTakeDayInterval());
                if(notificationTime >= end.getTimeInMillis() || med.getEnd().getTimeInMillis() < start.getTimeInMillis()) break;
                if(notificationTime >= start.getTimeInMillis() && notificationTime <= end.getTimeInMillis()) {
                    includedMeds.add(med);
                }
            }
        }
        return includedMeds;
    }

    /**
     * Moves date back to the start of the day
     * @param day day
     * @return returns moved date
     */
    private Calendar atDayStart(Calendar day) {
        Calendar clone = (Calendar) day.clone();
        clone.set(Calendar.HOUR_OF_DAY, 0);
        clone.set(Calendar.MINUTE, 0);
        clone.set(Calendar.SECOND, 0);
        clone.set(Calendar.MILLISECOND, 0);
        return clone;
    }

    /**
     * Moves date back to the end of the day
     * @param day day
     * @return returns moved date
     */
    private Calendar atDayEnd(Calendar day) {
        Calendar clone = atDayStart((Calendar) day.clone());
        clone.add(Calendar.DATE, 1);
        return clone;
    }
}
