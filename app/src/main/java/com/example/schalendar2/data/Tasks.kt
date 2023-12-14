package com.example.schalendar2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "events_table")
data class Tasks(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val date: LocalDate,
    val due: LocalTime,
    val details: String? = null,
    val oneHourBefore: Boolean,
    val threeHourBefore: Boolean,
    val oneDayBefore: Boolean
)

/*{Monday:{CS211:{Time:{9:00-11:00},Location:{CDS 46}},CS231:{Time:{13:00-16:45},Location:{CDS 46}},
    Tuesday:{CS411:{Time:{10:00-11:00},Location:{CDS 46}},CS231:{Time:{16:00-18:45},Location:{CDS 30}},
        Wedsnday:{CS211:{Time:{9:00-11:00},Location:{CDS 46}},CS231:{Time:{13:00-16:45},Location:{CDS 46}},
            Thursday:{CS411:{Time:{10:00-11:00},Location:{CDS 46}},CS231:{Time:{16:00-18:45},Location:{CDS 30}},
                Friday:{MA111:{Time:{8:00-9:00},Location:{CAS B06}}}*/