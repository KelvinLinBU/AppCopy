package com.example.schalendar2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity() {

    private fun startSpecificActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val submit = findViewById<Button>(R.id.submit)
        val destination = findViewById<TextView>(R.id.location)
        submit.setOnClickListener{
            getDistance("275 Babcock Street, Boston, MA", destination.text.toString())
        }
    }


    private fun getDistance(origin: String, dest: String) {
        val api = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/distancematrix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MapsApiService::class.java)

        api.getMapData(origin, dest, "AIzaSyDEM2pDWXuh-UJXl3RxL-QXv7uEAYjY828", "walking").enqueue(object : Callback<DistanceAndTimeResponse>{
            override fun onResponse(
                call: Call<DistanceAndTimeResponse>,
                response: Response<DistanceAndTimeResponse>
            ) {
                if (response.isSuccessful && response != null){

                    //findViewById<TextView>(R.id.mapDistance).text = response.body().toString()
                    //Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                    response.body()?.let {
                        findViewById<TextView>(R.id.mapDistance).text =  it.rows[0].elements[0].distance.text
                        findViewById<TextView>(R.id.mapTime).text = it.rows[0].elements[0].duration.text
                    }
                }
                else {
                    Toast.makeText(this@MapsActivity, "NUll", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<DistanceAndTimeResponse>, t: Throwable) {
                Log.i("Something", "onFailure: ${t.message}")
            }

        })
    }

    private fun handleUnsuccessfulResponse() {
        // Handle cases where the API response was not successful
        // For example, show an error message to the user
    }

    private fun handleFailure(t: Throwable) {
        // Handle failure in making the API call
        // For example, show an error message or log the error
    }

    private fun handleMapData(mapData: DistanceAndTimeResponse, rootView: View) {
        // Handle failure in making the API call
        // For example, show an error message or log the error
        Toast.makeText(this@MapsActivity, "Reaching", Toast.LENGTH_SHORT).show()
        val distanceTextView = rootView.findViewById<TextView>(R.id.mapDistance)
        val distanceTimeView = rootView.findViewById<TextView>(R.id.mapTime)

        distanceTextView.text = mapData.rows[0].elements[0].distance.text
        distanceTimeView.text = mapData.rows[0].elements[0].duration.text


    }
}

