package business;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.medsmemory.Application;

import java.util.Set;

public class AppSettingsStorage {
    private static AppSettingsStorage _instance = null;

    public enum Setting {
        DARK_MODE,
        NOTIFICATIONS_ENABLED,
        DAY_START,
        DAY_END
    }

    private SharedPreferences sharedPref = Application.getAppContext().getSharedPreferences("MEDS_MEMORY_SETTINGS", Context.MODE_PRIVATE);
    private SharedPreferences.Editor getEditor() {return sharedPref.edit();}

    private AppSettingsStorage() {}

    public static AppSettingsStorage getInstance() {
        if(_instance == null) AppSettingsStorage._instance = new AppSettingsStorage();
        return _instance;
    }

    public void remove(Setting setting) {
        getEditor().remove(setting.toString()).commit();
    }

    public void set(Setting setting, int value) {
        getEditor().putInt(setting.toString(), value).commit();
    }

    public void set(Setting setting, float value) {
        getEditor().putFloat(setting.toString(),value).commit();
    }

    public void set(Setting setting, boolean value) {
        getEditor().putBoolean(setting.toString(),value).commit();
    }

    public void set(Setting setting, long value) {
        getEditor().putLong(setting.toString(),value).commit();
    }

    public void set(Setting setting, String value) {
        getEditor().putString(setting.toString(),value).commit();
    }

    public void set(Setting setting, Set<String> value) {
        getEditor().putStringSet(setting.toString(),value).commit();
    }

    public int get(Setting setting, int defaultValue) {
        return sharedPref.getInt(setting.toString(), defaultValue);
    }

    public float get(Setting setting, float defaultValue) {
        return sharedPref.getFloat(setting.toString(), defaultValue);
    }

    public boolean get(Setting setting, boolean defaultValue) {
        return sharedPref.getBoolean(setting.toString(), defaultValue);
    }
    public long get(Setting setting, long defaultValue) {
        return sharedPref.getLong(setting.toString(), defaultValue);
    }
    public String get(Setting setting, String defaultValue) {
        return sharedPref.getString(setting.toString(), defaultValue);
    }
    public Set<String> get(Setting setting, Set<String> defaultValue) {
        return sharedPref.getStringSet(setting.toString(), defaultValue);
    }
}
