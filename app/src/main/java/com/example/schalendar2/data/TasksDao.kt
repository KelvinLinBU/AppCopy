package com.example.schalendar2.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TasksDao {
    @Query("SELECT * FROM events_table ORDER BY date,due")
    suspend fun getTasks():List<Tasks>

    @Query("SELECT * FROM events_table WHERE date = (:date) ORDER BY date,due")
    suspend fun getDayTasks(date: String):List<Tasks>

}