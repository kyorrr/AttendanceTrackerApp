package com.example.attendancetrackerapp.data

import androidx.room.*

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "employee_name") val employeeName: String,
    @ColumnInfo(name = "entry_time") val entryTime: Long,
    @ColumnInfo(name = "exit_time") val exitTime: Long?,
    @ColumnInfo(name = "date") val date: Long
) {
    init {
        require(entryTime <= (exitTime ?: Long.MAX_VALUE)) {
            "Время выхода не может быть раньше времени входа"
        }
    }
}