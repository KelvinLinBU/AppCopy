package com.example.schalendar2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course_table WHERE id = (:id)")
    suspend fun getCourse(id:Int): Course
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourse(course: Course)

}