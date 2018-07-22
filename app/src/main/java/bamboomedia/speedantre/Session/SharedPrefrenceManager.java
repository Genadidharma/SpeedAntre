package bamboomedia.speedantre.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceManager {
    private static final String SP_SpeedAntre = "SpeedAntreChace";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    public SharedPrefrenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_SpeedAntre, Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        sharedEditor.apply();
    }

    public void saveString(String key, String value){
        sharedEditor.putString(key, value);
        sharedEditor.apply();
    }

    public void saveBool(String key, boolean value){
        sharedEditor.putBoolean(key, value);
        sharedEditor.apply();
    }

    public void saveInt(String key, int value){
        sharedEditor.putInt(key, value);
        sharedEditor.apply();
    }

    public String getString(String key){
        return sharedPreferences.getString(key, "DEFAULT");
    }

    public boolean getBool(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public void deleteSharedPref(){
        sharedEditor.clear();
        sharedEditor.apply();
    }
}
