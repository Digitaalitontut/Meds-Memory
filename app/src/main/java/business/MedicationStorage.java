package business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.medsmemory.Application;

import java.util.ArrayList;

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

    public long create(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.insert(MedicationReaderContract.MedicationEntry.TABLE_NAME,null, medicationToContentValues(med));
    }

    private ContentValues medicationToContentValues(Medication med) {
        ContentValues values = new ContentValues();
        //values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME, med.getName()); TODO
        //values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_START, med.getName()); TODO
        //values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_END, med.getName()); TODO
        //values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_INTERVAL, med.getName()); TODO
        //values.put(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NOTIFICATION_SETTING, med.getName()); TODO
        return values;
    }

    public int update(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(
                MedicationReaderContract.MedicationEntry.TABLE_NAME,
                medicationToContentValues(med),
                MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME + "=?",
                new String[]{med.toString()} // TODO: FIX?
        );
    }


    public int delete(Medication med) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(MedicationReaderContract.MedicationEntry.TABLE_NAME, MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME+"=?", new String[]{med.toString()}); // TODO: fix where clause
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
            // TODO: finish mapping when Medication class is ready
           /* meds.add(new Medication(
                    cursor.getColumnIndex(MedicationReaderContract.MedicationEntry.COLUMN_NAME_NAME)
            ))
            */

        }
        cursor.close();

        return meds;
    }
}
