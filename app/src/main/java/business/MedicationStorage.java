package business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.medsmemory.Application;

import java.util.ArrayList;
import java.util.Calendar;

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
        values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_INTERVAL, med.getIntervalInMilliseconds());
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
         Medication med = new Medication(
                 cursor.getString(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME)),
                 start,
                 end,
                 cursor.getInt(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_INTERVAL)),
                 cursor.getFloat(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_DOSE)),
                 cursor.getString(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NOTES))
         );
         med.setId(cursor.getInt(cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_ID)));
         return med;
     }

    public ArrayList<Medication> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                MedicationReaderContract.MedicationEntry.TABLE_NAME,
                null,
                null,
                null,
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
}
