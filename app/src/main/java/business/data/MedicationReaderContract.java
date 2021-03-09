package business.data;

import android.provider.BaseColumns;

import business.Medication;

/**
 * Database contract for Medications
 */
public final class MedicationReaderContract {
    private MedicationReaderContract() {}

    /**
     * SQL statement to create medication entry table
     */
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MedicationEntry.TABLE_NAME + " (" +
            MedicationEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationEntry.COLUMN_NAME_NAME+" TEXT," +
            MedicationEntry.COLUMN_NAME_START+" INTEGER," +
            MedicationEntry.COLUMN_NAME_END+" INTEGER," +
            MedicationEntry.COLUMN_NAME_TAKEINTERVAL+" INTEGER," +
            MedicationEntry.COLUMN_NAME_TAKEDAYINTERVAL+" INTEGER," +
            MedicationEntry.COLUMN_NAME_DOSE+" INTEGER," +
            MedicationEntry.COLUMN_NAME_NOTES+" TEXT" +
                    ")";

    /**
     * SQL statement to create medication logs
     */
    public static final String SQL_CREATE_MED_LOG = "CREATE TABLE " + MedicationLog.TABLE_NAME + " ("+
            MedicationLog.COLUMN_NAME_MED_ID + " INTEGER," +
            MedicationLog.COLUMN_NAME_TAKEN_AT + " INTEGER)";

    /**
     * SQL statement to delete entry table
     */
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MedicationEntry.TABLE_NAME;
    /**
     * SQL statement to delete medication log
     */
    public static final String SQL_DELETE_MED_LOG = "DROP TABLE IF EXISTS " + MedicationLog.TABLE_NAME;

    /**
     * Defines table name and column names for entry table
     */
    public static class MedicationEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_TAKEDAYINTERVAL = "takeDayInterval";
        public static final String COLUMN_NAME_TAKEINTERVAL = "takeInterval";
        public static final String COLUMN_NAME_DOSE = "dose";
        public static final String COLUMN_NAME_NOTES = "notes";
    }

    /**
     * Defines table name and column names for medication logs
     */
    public static class MedicationLog implements  BaseColumns {
        public static final String TABLE_NAME = "medLog";
        public static final String COLUMN_NAME_MED_ID = "medID";
        public static final String COLUMN_NAME_TAKEN_AT = "takenAt";
    }

}
