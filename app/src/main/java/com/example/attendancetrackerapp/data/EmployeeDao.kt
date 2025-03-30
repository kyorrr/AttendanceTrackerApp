package com.example.attendancetrackerapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employees")
    fun getAll(): Flow<List<Employee>>
}