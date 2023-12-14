package com.example.schalendar2.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Building::class], version = 1)
abstract class BuildingDataBase : RoomDatabase(){
    abstract fun buildingDao(): BuildingDao
}