package com.example.schalendar2.data

import android.content.Context
import androidx.room.Room
private const val DATABASE_NAME = "events-database"
class TasksRepository private constructor(context: Context){

    private val database: TasksDataBase = Room.
    databaseBuilder(
            context.applicationContext,
            TasksDataBase::class.java,
            DATABASE_NAME
        ) .build()

    suspend fun getTasks():List<Tasks> = database.tasksDao().getTasks()
    suspend fun getDayTasks(date: String):List<Tasks> = database.tasksDao().getDayTasks(date)
    companion object {
        private var INSTANCE: TasksRepository? = null;

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE = TasksRepository(context)
            }
        }

        fun get(): TasksRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}