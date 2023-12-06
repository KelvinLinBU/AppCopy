package com.example.schalendar2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TimePicker
class EventInfo : AppCompatActivity() {
    private lateinit var classNameEditText: EditText
    private lateinit var classLocationEditText: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var submitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.eventinfo)
        super.onCreate(savedInstanceState)
        val spinner: Spinner = findViewById(R.id.priorityspinner)
        val options = resources.getStringArray(R.array.priority_array)
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        classNameEditText = findViewById(R.id.edittextclassname)
        classLocationEditText = findViewById(R.id.edittextclasslocation)
        datePicker = findViewById(R.id.datepickerclassdate)
        timePicker = findViewById(R.id.timepickerclasstime)
        submitButton = findViewById(R.id.savebutton)
        submitButton.setOnClickListener {
            val selectedDate = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"
            val enteredClassName = classNameEditText.text.toString()
            val enteredClassLocation = classLocationEditText.text.toString()
            val selectedSpinnerItem = spinner.selectedItem.toString()
            val intent = Intent()
            intent.putExtra("PRIORITY", selectedSpinnerItem)
            intent.putExtra("CLASS_NAME", enteredClassName)
            intent.putExtra("CLASS_LOCATION", enteredClassLocation)
            intent.putExtra("DATE", selectedDate)
            intent.putExtra("TIME", selectedTime)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}