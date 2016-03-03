package com.afra55.trainingfirstapp.base;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.afra55.trainingfirstapp.R;

/**
 * Created by yangshuai in the 23:38 of 2016.03.03 .
 */
public class MPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
    }
}
