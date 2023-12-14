package com.example.schalendar2

import android.app.Application
import com.example.schalendar2.data.BuildingRepository
import com.example.schalendar2.data.CourseRepository
import com.example.schalendar2.data.TasksRepository
import com.example.schalendar2.data.VacationRepository

class SchalendarIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BuildingRepository.initialize(this)
        CourseRepository.initialize(this)
        TasksRepository.initialize(this)
        VacationRepository.initialize(this)

    }
}