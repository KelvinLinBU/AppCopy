package com.example.schalendar2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "course_table")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    // 0 means course; 1 means lab; 2 means dis; 3 means oh; 4 means meeting
    val type: Int,
    val code: String? = null,
    val room: String? = null,
    val date: List<LocalDate>,
    val start: LocalTime,
    val end: LocalTime
)
