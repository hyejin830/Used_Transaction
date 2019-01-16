package com.with_tnt.myapplication3.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.util.Log;

import com.with_tnt.db_connection.settings.DB_UpdateHashtag;
import com.with_tnt.myapplication3.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_layout);



        SwitchPreference InternetStatus = (SwitchPreference) findPreference("Getnotice");
        InternetStatus.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor localEditor = getSharedPreferences("Datas", 0).edit(); // UserInfo
                localEditor.putBoolean("Getnotice", (Boolean) newValue); // UserID 컬럼
                localEditor.commit();
                return true;
            }
        });
        Log.e("text", InternetStatus.getKey().toString());

        Preference button = (Preference)getPreferenceManager().findPreference("hashtags");
        button.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor localEditor = getSharedPreferences("Datas", 0).edit(); // UserInfo
                localEditor.putString("Hashtag", String.valueOf(newValue)); // UserID 컬럼
                localEditor.commit();
                new DB_UpdateHashtag(getSharedPreferences("UserInfo", 0).getString("UserID", ""), String.valueOf(newValue)).execute();
                return true;
            }
        });
    }
}
