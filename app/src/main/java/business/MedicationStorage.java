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

public class MedicationStorage {

    private static MedicationStorage _instance = null;
    private MedicationReaderDbHelper dbHelper;

    private MedicationStorage(){
        dbHelper = new MedicationReaderDbHelper(Application.getAppContext());
    }

    public static MedicationStorage getInstance() {
        if(_instance == null) _instance = new MedicationStorage();
        return _instance;
    }

    public Medication insert(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        med.setId(db.insert(MedicationReaderContract.MedicationEntry.TABLE_NAME,null, medicationToContentValues(med)));
        return med;
    }

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

    public int update(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(
                MedicationReaderContract.MedicationEntry.TABLE_NAME,
                medicationToContentValues(med),
                MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID + "=?",
                new String[]{Long.toString(med.getId())}
        );
    }


    public int delete(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(MedicationReaderContract.MedicationEntry.TABLE_NAME, MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID+"=?", new String[]{Long.toString(med.getId())});
    }

    public Medication get(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MedicationReaderContract.MedicationEntry.TABLE_NAME + " WHERE " + MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID + " = " + Long.toString(id), null);
        if(c != null) c.moveToFirst();
        return medicationFromCursor(c);
     }

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

     public void insertLog(Medication med, Calendar takenAt) {
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(MedicationReaderContract.MedicationLog.COLUMN_NAME_MED_ID, med.getId());
         values.put(MedicationReaderContract.MedicationLog.COLUMN_NAME_TAKEN_AT, takenAt.getTimeInMillis());
         db.insert(MedicationReaderContract.MedicationLog.TABLE_NAME,null, values);
     }

     public int deleteLog(Medication med) {
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         return db.delete(MedicationReaderContract.MedicationLog.TABLE_NAME, MedicationReaderContract.MedicationLog.COLUMN_NAME_MED_ID+"=?", new String[]{Long.toString(med.getId())});
     }

     public long countLog(Medication med, Calendar start, Calendar end) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, MedicationReaderContract.MedicationLog.TABLE_NAME, MedicationReaderContract.MedicationLog.COLUMN_NAME_MED_ID + " = ? AND " +
                MedicationReaderContract.MedicationLog.COLUMN_NAME_TAKEN_AT + " > ? AND " +
                MedicationReaderContract.MedicationLog.COLUMN_NAME_TAKEN_AT + " < ?",
                new String[]{Long.toString(med.getId()), Long.toString(med.getStart().getTimeInMillis()), Long.toString(med.getEnd().getTimeInMillis())}
        );
     }

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

    private Calendar atDayStart(Calendar day) {
        Calendar clone = (Calendar) day.clone();
        clone.set(Calendar.HOUR_OF_DAY, 0);
        clone.set(Calendar.MINUTE, 0);
        clone.set(Calendar.SECOND, 0);
        clone.set(Calendar.MILLISECOND, 0);
        return clone;
    }
    private Calendar atDayEnd(Calendar day) {
        Calendar clone = atDayStart((Calendar) day.clone());
        clone.add(Calendar.DATE, 1);
        return clone;
    }
}
