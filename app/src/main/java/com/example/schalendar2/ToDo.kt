package com.example.schalendar2

import EventAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToDo : AppCompatActivity() {

    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventList: List<Event>
    private val REQUEST_CODE = 1
    private lateinit var button: Button
    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    fun receiveClassDetails(classDetails: Bundle) {
        val className = classDetails.getString("ClassName")
        val classLocation = classDetails.getString("ClassLocation")
        val classDate = classDetails.getString("ClassDate")
        val classTime = classDetails.getString("ClassTime")

        // Handle received class details (e.g., save to database, update UI, etc.)
        // Example: Display received class details in TextViews or perform actions
        //val classNameTextView: TextView = findViewById(R.id.classNameTextView)
        //val classLocationTextView: TextView = findViewById(R.id.classLocationTextView)
        //val classDateTimeTextView: TextView = findViewById(R.id.classDateTimeTextView)

        //classNameTextView.text = className
       // classLocationTextView.text = classLocation
        //classDateTimeTextView.text = "$classDate at $classTime"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo)

        val spinner: Spinner = findViewById(R.id.menuspinner)
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
                    "Help Center" -> startSpecificActivity(HelpCenter::class.java)
                    "View Calendar" -> startSpecificActivity(ViewCalendar::class.java)
                    "Initialiser" -> startSpecificActivity(Initialize::class.java)
                    "Paramètres" -> startSpecificActivity(Settings::class.java)
                    "Centre D'aide" -> startSpecificActivity(HelpCenter::class.java)
                    "Voir Le Calendrier" -> startSpecificActivity(ViewCalendar::class.java)
                    // Add more cases for other options if needed
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (if necessary)
            }
        }




        val recyclerView: RecyclerView = findViewById(R.id.eventrecycler_view)

        eventList = generateEventList()

        recyclerView.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter(eventList)
        recyclerView.adapter = eventAdapter
        findWeather()
        val  buttonAddClass:Button = findViewById(R.id.addclassbutton)
        buttonAddClass.setOnClickListener {
            val intent = Intent(this, EventInfo::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val className = data?.getStringExtra("CLASS_NAME")
            val classLocation = data?.getStringExtra("CLASS_LOCATION")
            val date = data?.getStringExtra("DATE")
            val time = data?.getStringExtra("TIME")
            val priority = data?.getIntExtra("PRIORITY", 1) ?: 1 // Default to 1 if no value is received
            Toast.makeText(this@ToDo, time, Toast.LENGTH_SHORT).show()
            val event = Event(className.toString(), classLocation.toString(), time.toString(), priority)
            eventList.add(event)

            //recyclerView.layoutManager = LinearLayoutManager(this)
            //eventAdapter = EventAdapter(eventList)
            //recyclerView.adapter = eventAdapter
            //eventAdapter.notifyDataSetChanged()
        }
    }


    private fun generateEventList(): List<Event> {
        val events = mutableListOf<Event>()
        events.add(Event("Event 1", "Course A", "10:00 AM", 1))
        events.add(Event("Event 2", "Course B", "2:00 PM", 2))
        events.add(Event("Event 3", "Course C", "4:30 PM", 3))
        events.add(Event("Event 3", "Course C", "4:30 PM", 1))
        events.add(Event("Event 1", "Course A", "10:00 AM", 1))
        events.add(Event("Event 2", "Course B", "2:00 PM", 2))
        events.add(Event("Event 3", "Course C", "4:30 PM", 3))
        events.add(Event("Event 3", "Course C", "4:30 PM", 1))
        return events
    }
    private fun findWeather() {
        val apiService = RetrofitClient.getRetrofitInstance().create(WeatherApiService::class.java)

        val call: Call<WeatherResponse> = apiService.getWeatherData("Boston", "f59dbd3ae07ebb65b3538ca10a8c2cb7")

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val weatherData: WeatherResponse = response.body()!!
                    // Handle the weather data here
                    val rootView: View = findViewById(android.R.id.content)
                    handleWeatherData(weatherData, rootView)
                } else {
                    // Handle unsuccessful response
                    handleUnsuccessfulResponse()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Handle failure
                handleFailure(t)
            }
        })
    }
}

private fun <Event> List<Event>.add(event: Event) {

}

private fun handleWeatherData(weatherData: WeatherResponse, rootView: View) {
    fun getWindDirection(degrees: Double): String {
        val directions = arrayOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
        val index = ((degrees % 360) / 45).toInt()
        return directions[index]
    }
    // Process the weather data
    val weatherDescription = weatherData.weather.firstOrNull()?.description ?: "No description available"
    val kelvintemperature =  Math.ceil(weatherData.main.temp)
    val celsuis = kelvintemperature - 273.15
    val celsuistemperature = Math.ceil( kelvintemperature - 273.15)
    val farenheittemperature =  Math.ceil(celsuistemperature * 9 / 5 + 32)
    // Find the TextViews using the rootView
    val weatherDescriptionTextView: TextView = rootView.findViewById(R.id.weatherdescription)
    val temperatureTextView: TextView = rootView.findViewById(R.id.weathertemp)
    val windSpeed = weatherData.wind.speed // Wind speed in meters per second
    val recommendation = getTemperatureRecommendation(celsuis)
    // Assuming you have a TextView to display wind speed
    val windSpeedTextView: TextView = rootView.findViewById(R.id.weatherwindspeed)
    val recommendationTextView: TextView = rootView.findViewById(R.id.weatherrecommendation)
    // Display wind speed in km/h (convert from meters/second)
    val windSpeedKmh = windSpeed * 3.6 // Conversion factor: 1 m/s = 3.6 km/h
    windSpeedTextView.text = "Windspeed: ${"%.2f".format(windSpeedKmh)} km/h"
    // Update UI with weather information
    weatherDescriptionTextView.text = weatherDescription
    temperatureTextView.text = "$farenheittemperature °F"
    recommendationTextView.text = recommendation
}

private fun handleUnsuccessfulResponse() {
    // Handle cases where the API response was not successful
    // For example, show an error message to the user
}

private fun handleFailure(t: Throwable) {
    // Handle failure in making the API call
    // For example, show an error message or log the error
}

fun getTemperatureRecommendation(temperature: Double): String {
    return when {
        temperature < 0 -> "It's very cold! Bundle up!"
        temperature in 0.0..10.0 -> "It's quite chilly. Wear a coat!"
        temperature in 10.0..20.0 -> "It's cool. A jacket might be good."
        temperature in 20.0..30.0 -> "It's pleasant. Enjoy the weather!"
        temperature in 30.0..35.0 -> "It's warm. T-shirt weather!"
        temperature > 35.0 -> "It's hot! Find shade!"
        else -> "Temperature information unavailable."
    }
}


