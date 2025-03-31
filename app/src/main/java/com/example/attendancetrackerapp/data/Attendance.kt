package com.example.attendancetrackerapp.data

import androidx.room.*

@Entity(
    tableName = "attendance",
    foreignKeys = [
        ForeignKey(
            entity = Employee::class,
            parentColumns = ["id"],
            childColumns = ["employee_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("employee_id")]
)

data class Attendance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "employee_id") val employeeId: Int,
    @ColumnInfo(name = "entry_time") val entryTime: Long,
    @ColumnInfo(name = "exit_time") val exitTime: Long?,
    @ColumnInfo(name = "date") val date: Long
)