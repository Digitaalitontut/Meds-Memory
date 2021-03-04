package business.data;

import android.provider.BaseColumns;

import business.Medication;

public final class MedicationReaderContract {
    private MedicationReaderContract() {}

    public static final String SQL_CREATE_ENTRIES = String.format(
            "CREATE TABLE %s (" + // TABLE_NAME
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," + // COLUMN_NAME_ID
                    " %s TEXT," + // COLUMN_NAME_NAME
                    " %s INTEGER," + // COLUMN_NAME_START
                    " %s INTEGER," + // COLUMN_NAME_END
                    " %s INTEGER," + // COLUMN_NAME_INTERVAL
                    " %s INTEGER," + // COLUMN_NAME_DOSE
                    " %s TEXT" + // COLUMN_NAME_NOTES
                    ")",
            MedicationEntry.TABLE_NAME,
            MedicationEntry.COLUMN_NAME_ID,
            MedicationEntry.COLUMN_NAME_NAME,
            MedicationEntry.COLUMN_NAME_START,
            MedicationEntry.COLUMN_NAME_END,
            MedicationEntry.COLUMN_NAME_INTERVAL,
            MedicationEntry.COLUMN_NAME_DOSE,
            MedicationEntry.COLUMN_NAME_NOTES
    );


    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MedicationEntry.TABLE_NAME;

    public static class MedicationEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_INTERVAL = "interval";
        public static final String COLUMN_NAME_DOSE = "dose";
        public static final String COLUMN_NAME_NOTES = "notes";
    }

}
