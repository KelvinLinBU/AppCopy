package com.example.schalendar2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity() {

    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }

    private fun findDistance() {
        val apiService = RetrofitClient.getRetrofitInstance().create(MapsApiService::class.java)

        val call: Call<Result> = apiService.getMapData("AIzaSyDEM2pDWXuh-UJXl3RxL-QXv7uEAYjY828", "275 Babcock Street, Boston, MA", "1037 Commonwealth Avenue, Boston, MA")

        /*call.enqueue(object : Callback<Result> {
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
        })*/
    }

    private fun handleUnsuccessfulResponse() {
        // Handle cases where the API response was not successful
        // For example, show an error message to the user
    }

    private fun handleFailure(t: Throwable) {
        // Handle failure in making the API call
        // For example, show an error message or log the error
    }
}

