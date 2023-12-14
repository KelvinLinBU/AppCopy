package com.example.schalendar2.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalTime

class TypeConverter {
    @TypeConverter
    fun fromDate(date:LocalDate):String{
        return date.toString()
    }
    @TypeConverter
    fun toDate(sd:String):LocalDate{
        return LocalDate.parse(sd)
    }
    @TypeConverter
    fun fromTime(date:LocalTime):String{
        return date.toString()
    }
    @TypeConverter
    fun toTime(sd:String):LocalTime{
        return LocalTime.parse(sd)
    }
    @TypeConverter
    fun fromListDate(dateList: List<LocalDate>): String {
        val gson = Gson()
        return gson.toJson(dateList)
    }

    @TypeConverter
    fun toListDate(dateString: String): List<LocalDate> {
        val gson = Gson()
        val type = object : TypeToken<List<LocalDate>>() {}.type
        return gson.fromJson(dateString, type)
    }
}