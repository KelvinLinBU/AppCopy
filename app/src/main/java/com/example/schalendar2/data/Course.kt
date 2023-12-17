package com.example.schalendar2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "course_table")
data class Course(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "",
    var type: Int = 0,
    var code: String? = null,
    var room: String? = null,
    var date: String = "Monday And Tuesday?" ,
    var start: String = "",
    var end: String = ""
)
