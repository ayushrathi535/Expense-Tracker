package com.example.expensetracker.data.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.util.Date

class DateConverter {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time?.toLong()
        }
    }
