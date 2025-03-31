package com.example.attendancetrackerapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT * FROM employees WHERE login = :login AND password = :password")
    suspend fun findEmployee(login: String, password: String): Employee?

    @Query("SELECT * FROM employees")
    fun getAll(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE id = :id")
    suspend fun findById(id: Int): Employee?
}