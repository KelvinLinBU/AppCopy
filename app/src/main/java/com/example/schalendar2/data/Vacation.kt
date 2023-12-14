package com.example.schalendar2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "vacation_table")
data class Vacation(
    @PrimaryKey(autoGenerate = false)
    val date: LocalDate,
    // term: 0 means start; 1 means end; 2 means holiday; 3 means previous day's schedule.
    val type: Int,
    val detail: String,
    val year: Int,
    // term: 0 means spring; 1 means summer1; 2 means summer2; 3 means fall.
    val term: Int
)
