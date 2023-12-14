package com.example.schalendar2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Vacation::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class VacationDataBase : RoomDatabase(){
    abstract fun vacationDao():VacationDao
}