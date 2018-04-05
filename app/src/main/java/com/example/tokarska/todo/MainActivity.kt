package com.example.tokarska.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    var m: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.main_layout, RecycleFragment()).addToBackStack(null).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        m = menu
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "Settings"
            m!!.findItem(R.id.action_settings).isEnabled = false
            m!!.findItem(R.id.action_settings).isVisible = false
            supportFragmentManager.beginTransaction().replace(R.id.main_layout, SettingsFragment()).addToBackStack("display_preferences_transaction").commit()
            true
        }
        android.R.id.home -> {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.title="ToDo"
            m!!.findItem(R.id.action_settings).isEnabled = true
            m!!.findItem(R.id.action_settings).isVisible = true
            onBackPressed()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
