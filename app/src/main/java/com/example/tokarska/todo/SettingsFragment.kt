package com.example.tokarska.todo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager

class SettingsFragment : PreferenceFragmentCompat() , SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

    }

    var sharedPreferences: SharedPreferences? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
       // sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        //preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }
}
