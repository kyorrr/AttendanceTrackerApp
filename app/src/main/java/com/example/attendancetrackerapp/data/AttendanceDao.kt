package com.example.attendancetrackerapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert
    suspend fun insert(attendance: Attendance)

    @Query("SELECT * FROM attendance ORDER BY date DESC")
    fun getAll(): Flow<List<Attendance>>

    @Query("SELECT * FROM attendance WHERE employee_name = :name")
    fun getAttendanceForEmployee(name: String): Flow<List<Attendance>>
}