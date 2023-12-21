package com.example.schalendar2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApiService {
    @GET("json")
    fun getMapData(
        @Query("origins") origin: String,
        @Query("destinations") destination: String,
        @Query("key") key: String,
        @Query("mode") mode: String
    ): Call<DistanceAndTimeResponse>
}
