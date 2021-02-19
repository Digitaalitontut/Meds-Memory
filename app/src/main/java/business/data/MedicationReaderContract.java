package business.data;

import android.provider.BaseColumns;

import business.Medication;

public final class MedicationReaderContract {
    private MedicationReaderContract() {}

    public static final String SQL_CREATE_ENTRIES = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY," + // TABLE_NAME
                    " %s TEXT," + // COLUMN_NAME_NAME
                    " %s TEXT," + // COLUMN_NAME_START
                    " %s INTEGER," + // COLUMN_NAME_END
                    " %s TEXT" + // COLUMN_NAME_NOTIFICATION_SETTING
                    ")",
            MedicationEntry.TABLE_NAME,
            MedicationEntry.COLUMN_NAME_NAME,
            MedicationEntry.COLUMN_NAME_START,
            MedicationEntry.COLUMN_NAME_END,
            MedicationEntry.COLUMN_NAME_INTERVAL,
            MedicationEntry.COLUMN_NAME_NOTIFICATION_SETTING
    );


    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MedicationEntry.TABLE_NAME;

    public static class MedicationEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_START = "start";
        public static final String COLUMN_NAME_END = "end";
        public static final String COLUMN_NAME_INTERVAL = "interval";
        public static final String COLUMN_NAME_NOTIFICATION_SETTING = "notificationSetting";
    }

}
