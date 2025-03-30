package com.example.attendancetrackerapp.data

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Attendance::class], version = 1,
    autoMigrations = [], exportSchema = false)
@TypeConverters(Converters::class)
abstract class AttendanceDatabase : RoomDatabase() {
    abstract fun attendanceDao(): AttendanceDao

    companion object {
        @Volatile
        private var INSTANCE: AttendanceDatabase? = null

        fun getInstance(context: Context): AttendanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AttendanceDatabase::class.java,
                    "attendance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
