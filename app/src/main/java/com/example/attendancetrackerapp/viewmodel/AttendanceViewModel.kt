package com.example.attendancetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancetrackerapp.data.Attendance
import com.example.attendancetrackerapp.data.AttendanceDatabase
import com.example.attendancetrackerapp.data.Employee
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.core.content.edit

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AttendanceDatabase.getInstance(application)
    private val attendanceDao = database.attendanceDao()
    private val employeeDao = database.employeeDao()

    private val _darkTheme = MutableStateFlow(false)
    val darkTheme = _darkTheme.asStateFlow()

    init {
        val savedTheme = getApplication<Application>().getSharedPreferences("prefs", 0)
            .getBoolean("dark_theme", false)
        _darkTheme.value = savedTheme
    }

    fun toggleTheme(isDark: Boolean) {
        _darkTheme.value = isDark
        getApplication<Application>().getSharedPreferences("prefs", 0).edit() {
            putBoolean("dark_theme", isDark)
        }
    }

    fun insert(attendance: Attendance) = viewModelScope.launch {
        attendanceDao.insert(attendance)
    }

    val employees = employeeDao.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun insertEmployee(employee: Employee) = viewModelScope.launch {
        employeeDao.insert(employee)
    }

    fun deleteEmployee(employee: Employee) = viewModelScope.launch {
        employeeDao.delete(employee)
    }
}