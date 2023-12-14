package com.example.schalendar2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "building_table")
data class Building(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val address: String
)
