package com.monse.andrea.proyectofinal.preferences;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.util.Log;

import com.monse.andrea.proyectofinal.R;

public class PreferenciasActitvity extends PreferenceActivity
{
    private CheckBoxPreference auto_preference;
    private EditTextPreference placas_preference;
    private EditTextPreference color_preference;
    private EditTextPreference marca_preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        iniciar();

        auto_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                if(newValue.toString().equals("true"))
                {
                    Log.d("MyApp", "Pref " + preference.getKey() + " changed to " + newValue.toString());
                    placas_preference.setEnabled(true);
                    color_preference.setEnabled(true);
                    marca_preference.setEnabled(true);
                }
                else
                {
                    placas_preference.setEnabled(false);
                    color_preference.setEnabled(false);
                    marca_preference.setEnabled(false);
                }
                return true;
            }
        });
    }

    private void iniciar()
    {
        auto_preference = (CheckBoxPreference)getPreferenceManager().findPreference("auto");
        placas_preference = (EditTextPreference)getPreferenceManager().findPreference("placas");
        color_preference = (EditTextPreference)getPreferenceManager().findPreference("color");
        marca_preference = (EditTextPreference)getPreferenceManager().findPreference("marca");
    }

}
