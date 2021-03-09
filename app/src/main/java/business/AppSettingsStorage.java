package business;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medsmemory.Application;

import java.util.Set;

/**
 * Singleton to handle Application settings
 */
public class AppSettingsStorage {
    private static AppSettingsStorage _instance = null;

    /**
     * Keys for all the settings
     */
    public enum Setting {
        DARK_MODE,
        NOTIFICATIONS_ENABLED,
        DAY_START,
        DAY_END
    }

    private SharedPreferences sharedPref = Application.getAppContext().getSharedPreferences("MEDS_MEMORY_SETTINGS", Context.MODE_PRIVATE);
    private SharedPreferences.Editor getEditor() {return sharedPref.edit();}

    private AppSettingsStorage() {}

    /**
     * Creates AppSettingsStorage if it doesn't exist and returns it.
     * @return Returns AppSettingsStorage
     */
    public static AppSettingsStorage getInstance() {
        if(_instance == null) AppSettingsStorage._instance = new AppSettingsStorage();
        return _instance;
    }

    /**
     * Removes setting
     * @param setting setting to be removed
     */
    public void remove(Setting setting) {
        getEditor().remove(setting.toString()).commit();
    }

    /**
     * Set setting that is type of int
     * @param setting Setting to be set
     * @param value value for the setting
     */
    public void set(Setting setting, int value) {
        getEditor().putInt(setting.toString(), value).commit();
    }

    /**
     * Set setting that is type of float
     * @param setting Setting to be set
     * @param value value for the setting
     */
    public void set(Setting setting, float value) {
        getEditor().putFloat(setting.toString(),value).commit();
    }
    /**
     * Set setting that is type of boolean
     * @param setting Setting to be set
     * @param value value for the setting
     */
    public void set(Setting setting, boolean value) {
        getEditor().putBoolean(setting.toString(),value).commit();
    }

    /**
     * Set setting that is type of long
     * @param setting Setting to be set
     * @param value value for the setting
     */
    public void set(Setting setting, long value) {
        getEditor().putLong(setting.toString(),value).commit();
    }
    /**
     * Set setting that is type of String
     * @param setting Setting to be set
     * @param value value for the setting
     */
    public void set(Setting setting, String value) {
        getEditor().putString(setting.toString(),value).commit();
    }

    /**
     * Set setting that is type of String set
     * @param setting Setting to be set
     * @param value value for the setting
     */
    public void set(Setting setting, Set<String> value) {
        getEditor().putStringSet(setting.toString(),value).commit();
    }

    /**
     * Gets setting that is type of int
     * @param setting Setting to get
     * @param defaultValue Default value for this setting
     * @return returns value of the setting
     */
    public int get(Setting setting, int defaultValue) {
        return sharedPref.getInt(setting.toString(), defaultValue);
    }
    /**
     * Gets setting that is type of float
     * @param setting Setting to get
     * @param defaultValue Default value for this setting
     * @return returns value of the setting
     */
    public float get(Setting setting, float defaultValue) {
        return sharedPref.getFloat(setting.toString(), defaultValue);
    }
    /**
     * Gets setting that is type of boolean
     * @param setting Setting to get
     * @param defaultValue Default value for this setting
     * @return returns value of the setting
     */
    public boolean get(Setting setting, boolean defaultValue) {
        return sharedPref.getBoolean(setting.toString(), defaultValue);
    }
    /**
     * Gets setting that is type of long
     * @param setting Setting to get
     * @param defaultValue Default value for this setting
     * @return returns value of the setting
     */
    public long get(Setting setting, long defaultValue) {
        return sharedPref.getLong(setting.toString(), defaultValue);
    }
    /**
     * Gets setting that is type of String
     * @param setting Setting to get
     * @param defaultValue Default value for this setting
     * @return returns value of the setting
     */
    public String get(Setting setting, String defaultValue) {
        return sharedPref.getString(setting.toString(), defaultValue);
    }
    /**
     * Gets setting that is type of String set
     * @param setting Setting to get
     * @param defaultValue Default value for this setting
     * @return returns value of the setting
     */
    public Set<String> get(Setting setting, Set<String> defaultValue) {
        return sharedPref.getStringSet(setting.toString(), defaultValue);
    }
}
