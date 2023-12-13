package com.example.schalendar2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ViewCalendar : AppCompatActivity() {
    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.viewcalendar)
        super.onCreate(savedInstanceState)
        val spinner: Spinner = findViewById(R.id.menuspinnerviewcalendar)
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
                    "Settings" -> startSpecificActivity(Settings::class.java)
                    "To-Do" -> startSpecificActivity(ToDo::class.java)
                    "Help Center" -> startSpecificActivity(HelpCenter::class.java)
                    "Initialiser" -> startSpecificActivity(Initialize::class.java)
                    "Paramètres" -> startSpecificActivity(Settings::class.java)
                    "Centre D'aide" -> startSpecificActivity(HelpCenter::class.java)
                    "Liste des Tâches" -> startSpecificActivity(ToDo::class.java)
                    "初始化" -> startSpecificActivity(Initialize::class.java)
                    "设置" -> startSpecificActivity(Settings::class.java)
                    "帮助中心" -> startSpecificActivity(HelpCenter::class.java)
                    "去做" -> startSpecificActivity(ToDo::class.java)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (if necessary)
            }
        }
    }
}