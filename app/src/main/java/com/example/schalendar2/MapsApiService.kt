package com.example.schalendar2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApiService {
    @GET("maps/api/distancematrix/json")
    fun getMapData(
        @Query("key") key: String,
        @Query("origins") origin: String,
        @Query("destinations") destination: String,
    ): Call<Result>
}
