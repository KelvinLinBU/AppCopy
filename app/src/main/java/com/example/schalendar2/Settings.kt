package com.example.schalendar2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.settings)
        super.onCreate(savedInstanceState)
        val spinner: Spinner = findViewById(R.id.menuspinnersettings)
        val options = resources.getStringArray(R.array.menu_array)

        ArrayAdapter.createFromResource(
            this,
            R.array.menu_array,
            R.layout.spinner_list
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_list)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item from the spinner
                val selectedItem: String = options[position]
                // Start the corresponding activity based on the selected item
                when (selectedItem) {
                    "Initialize Calendar" -> startSpecificActivity(Initialize::class.java)
                    "To-Do" -> startSpecificActivity(ToDo::class.java)
                    "Help Center" -> startSpecificActivity(HelpCenter::class.java)
                    "View Calendar" -> startSpecificActivity(ViewCalendar::class.java)
                    // Add more cases for other options if needed
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (if necessary)
            }
        }
        val colorspinner: Spinner = findViewById(R.id.colorspinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.color_options_array,
            R.layout.spinner_list
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_list)
            colorspinner.adapter = adapter
        }
        val languagespinner: Spinner = findViewById(R.id.languagespinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.language_options_array,
            R.layout.spinner_list
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_list)
            languagespinner.adapter = adapter
        }
    }
}