package com.example.schalendar2.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Tasks::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class TasksDataBase : RoomDatabase(){
    abstract fun tasksDao(): TasksDao
}