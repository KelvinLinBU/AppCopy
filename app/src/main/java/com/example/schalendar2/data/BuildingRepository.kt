package com.example.schalendar2.data

import android.content.Context
import androidx.room.Room
private const val DATABASE_NAME = "building-database"

class BuildingRepository private constructor(context: Context){

    private val database: BuildingDataBase = Room.
    databaseBuilder(
        context.applicationContext,
        BuildingDataBase::class.java,
        DATABASE_NAME
    ).build()

    suspend fun getAddress(code:String): Building = database.buildingDao().getAddress(code)
    companion object {
        private var INSTANCE: BuildingRepository? = null;

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE = BuildingRepository(context)
            }
        }

        fun get(): BuildingRepository {
            return INSTANCE ?:
            throw IllegalStateException("BuildingRepository must be initialized")
        }
    }
}