package com.example.schalendar2


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.schalendar2.R.id.helpcenter_layout


class Settings : AppCompatActivity() {
    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        val spinner: Spinner = findViewById(R.id.menuspinnersettings)
        val options = resources.getStringArray(R.array.menu_array)
        val color_options = resources.getStringArray(R.array.color_options_array)
        val language_options = resources.getStringArray(R.array.language_options_array)

        ArrayAdapter.createFromResource(
            this,
            R.array.menu_array,
            R.layout.spinner_list
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_list)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Get the selected item from the spinner
                val selectedItem: String = options[position]
                // Start the corresponding activity based on the selected item
                when (selectedItem) {
                    "Initialize Calendar" -> startSpecificActivity(Initialize::class.java)
                    "Settings" -> startSpecificActivity(Settings::class.java)
                    "To-Do" -> startSpecificActivity(ToDo::class.java)
                    "View Calendar" -> startSpecificActivity(ViewCalendar::class.java)
                    "Initialiser" -> startSpecificActivity(Initialize::class.java)
                    "Liste des Tâches" -> startSpecificActivity(ToDo::class.java)
                    "Centre D'aide" -> startSpecificActivity(HelpCenter::class.java)
                    "Voir Le Calendrier" -> startSpecificActivity(ViewCalendar::class.java)
                    "初始化" -> startSpecificActivity(Initialize::class.java)
                    "查看日历" -> startSpecificActivity(ViewCalendar::class.java)
                    "帮助中心" -> startSpecificActivity(HelpCenter::class.java)
                    "去做" -> startSpecificActivity(ToDo::class.java)
                    // Add more cases for other options if needed
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        val colorSpinner: Spinner = findViewById(R.id.colorspinner)
        val languageSpinner: Spinner = findViewById(R.id.languagespinner)

        // Populate color spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.color_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            colorSpinner.adapter = adapter
        }

        // Populate language spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.language_options_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            languageSpinner.adapter = adapter
        }

        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedColor: String = color_options[position]
                var signuppage = findViewById<View>(R.id.signup_layout)
                //var helpcenterpage = findViewById<View>(R.id.helpcenter_layout)
                var todopage = findViewById<View>(R.id.todo_layout)
                var settingspage = findViewById<View>(R.id.settings_layout)
                //var initializepage = findViewById<View>(R.id.initialize_layout)
                var viewcalendarpage = findViewById<View>(R.id.viewcalendar_layout)
                when (selectedColor) {
                    "Blue" -> {
                        // Change background color to blue
                        //THE ISSUE IS THAT IT CANNOT ACCESS ACTIVITIES THAT IS NOT ITSELF
                        // Replace R.id.yourLinearLayout with the actual ID of your LinearLayout
                        Toast.makeText(this@Settings, "Blue", Toast.LENGTH_SHORT).show()
                        changeBackgroundColor(settingspage, "#2DA9EC")
                        //changeBackgroundColor(helpcenterpage, "#2DA9EC")
                      //  changeBackgroundColor(initializepage, "#2DA9EC")
                       // changeBackgroundColor(viewcalendarpage, "#2DA9EC")
                        //changeBackgroundColor(signuppage, "#2DA9EC")
                        //changeBackgroundColor(todopage, "#2DA9EC")
                    }
                    "Salmon" -> {
                        // Change background color to salmon
                        Toast.makeText(this@Settings, "Salmon", Toast.LENGTH_SHORT).show()
                        changeBackgroundColor(settingspage, "#E3414E")
                        //changeBackgroundColor(helpcenterpage, "#E3414E")
                       // changeBackgroundColor(initializepage, "#E3414E")
                        //changeBackgroundColor(viewcalendarpage, "#E3414E")
                        //changeBackgroundColor(signuppage, "#E3414E")
                       // changeBackgroundColor(todopage, "#E3414E")
                    }
                    "Yellow" -> {
                        // Change background color to yellow
                        Toast.makeText(this@Settings, "Yellow", Toast.LENGTH_SHORT).show()
                        changeBackgroundColor(settingspage, "#F1D416")
                        //changeBackgroundColor(helpcenterpage, "#F1D416")
                     //   changeBackgroundColor(initializepage, "#F1D416")
                        //changeBackgroundColor(viewcalendarpage, "#F1D416")
                        //changeBackgroundColor(signuppage, "#F1D416")
                    //    changeBackgroundColor(todopage, "#F1D416")
                    }
                    "Green" -> {
                        Toast.makeText(this@Settings, "Green", Toast.LENGTH_SHORT).show()
                        changeBackgroundColor(settingspage, "#17C26F")
                        //changeBackgroundColor(helpcenterpage, "#17C26F")
                      //  changeBackgroundColor(initializepage, "#17C26F")
                       // changeBackgroundColor(viewcalendarpage, "#17C26F")
                      //  changeBackgroundColor(signuppage, "#17C26F")
                      //  changeBackgroundColor(todopage, "#17C26F")

                    }
                    // Add more cases for other options if needed
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (if necessary)
            }
        }




        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage: String = resources.getStringArray(R.array.language_options_array)[position]

                when (selectedLanguage) {
                    "English" -> {
                        // Change language to English (if applicable)
                        LocaleManager.setLocale(resources, "en")
                    }
                    "French" -> {
                        // Change language to French (if applicable)
                        LocaleManager.setLocale(resources, "fr")
                    }
                    // Add more cases for other language options if needed
                    "Chinese" -> {
                        // Change language to French (if applicable)
                        LocaleManager.setLocale(resources, "zh")
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (if necessary)
            }
        }
    }
    fun changeBackgroundColor(view: View, colorHex: String) {
        val color = Color.parseColor(colorHex)
        view.setBackgroundColor(color)
    }
}
