package com.example.schalendar2.data

import android.content.Context
import androidx.room.Room
private const val DATABASE_NAME = "course-database"

class CourseRepository private constructor(context: Context){

    private val database: CourseDataBase = Room.
    databaseBuilder(
        context.applicationContext,
        CourseDataBase::class.java,
        DATABASE_NAME
    ) .build()

    suspend fun getCourse(id:Int): Course = database.courseDao().getCourse(id)
    companion object {
        private var INSTANCE: CourseRepository? = null;

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE = CourseRepository(context)
            }
        }

        fun get(): CourseRepository {
            return INSTANCE ?:
            throw IllegalStateException("CourseRepository must be initialized")
        }
    }
}