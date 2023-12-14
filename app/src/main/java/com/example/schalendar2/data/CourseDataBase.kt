package com.example.schalendar2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Course::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class CourseDataBase : RoomDatabase(){
    abstract fun courseDao(): CourseDao
}