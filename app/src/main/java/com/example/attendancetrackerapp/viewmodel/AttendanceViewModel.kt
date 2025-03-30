package com.example.attendancetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancetrackerapp.data.Attendance
import com.example.attendancetrackerapp.data.AttendanceDatabase
import com.example.attendancetrackerapp.data.Employee
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AttendanceDatabase.getInstance(application)
    private val employeeDao = database.employeeDao()

    val employees = employeeDao.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    fun insertAttendance(attendance: Attendance) = viewModelScope.launch {
        database.attendanceDao().insert(attendance)
    }
}