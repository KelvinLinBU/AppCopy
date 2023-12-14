package com.example.schalendar2.data

import android.content.Context
import androidx.room.Room
private const val DATABASE_NAME = "vacation-database"

class VacationRepository private constructor(context: Context){

    private val database: VacationDataBase = Room.
    databaseBuilder(
        context.applicationContext,
        VacationDataBase::class.java,
        DATABASE_NAME
    ) .build()

    suspend fun getStartDate(year: Int,term: Int): String? = database.vacationDao().getStartDate(year,term)
    suspend fun getEndDate(year: Int,term: Int): String? = database.vacationDao().getEndDate(year,term)
    suspend fun getFreeDate(year: Int,term: Int): List<String>? = database.vacationDao().getFreeDate(year,term)
    suspend fun getSwitchDate(year: Int,term: Int): List<String>? = database.vacationDao().getSwitchDate(year,term)
    companion object {
        private var INSTANCE: VacationRepository? = null;

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE = VacationRepository(context)
            }
        }

        fun get(): VacationRepository {
            return INSTANCE ?:
            throw IllegalStateException("VacationRepository must be initialized")
        }
    }
}