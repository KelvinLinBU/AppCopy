package com.example.schalendar2.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface BuildingDao {
    @Query("SELECT * From building_table WHERE code = (:code)")
    suspend fun getAddress(code:String): Building
}