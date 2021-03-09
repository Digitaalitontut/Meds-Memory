package business.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database helper for the application
 */
public class MedicationReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Medication.db";

    /**
     * Constructor for MedicationReaderDbHelper
     * @param context Application context
     */
    public MedicationReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates entry and medication tables based on SQL statements from MedicationReaderContract
     * @param db database where to execute commands
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MedicationReaderContract.SQL_CREATE_ENTRIES);
        db.execSQL(MedicationReaderContract.SQL_CREATE_MED_LOG);
    }

    /**
     * Deletes entry and medication tables based on SQL statements from MedicationReaderContract
     * @param db database where to execute commands
     * @param oldVersion older version
     * @param newVersion new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MedicationReaderContract.SQL_DELETE_ENTRIES);
        db.execSQL(MedicationReaderContract.SQL_DELETE_MED_LOG);
        onCreate(db);
    }

    /**
     * Deletes entry and medication tables based on SQL statements from MedicationReaderContract
     * @param db database where to execute commands
     * @param oldVersion older version
     * @param newVersion new version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
