package com.example.schalendar2.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface VacationDao {
    @Query("SELECT date FROM vacation_table WHERE year = (:year) AND term = (:term) AND type = 0 ")
    suspend fun getStartDate(year: Int,term: Int): String?

    @Query("SELECT date FROM vacation_table WHERE year = (:year) AND term = (:term) AND type = 1 ")
    suspend fun getEndDate(year: Int,term: Int): String?

    @Query("SELECT date FROM vacation_table WHERE year = (:year) AND term = (:term) AND type = 2 ")
    suspend fun getFreeDate(year: Int,term: Int): List<String>?

    @Query("SELECT date FROM vacation_table WHERE year = (:year) AND term = (:term) AND type = 3 ")
    suspend fun getSwitchDate(year: Int,term: Int): List<String>?
}