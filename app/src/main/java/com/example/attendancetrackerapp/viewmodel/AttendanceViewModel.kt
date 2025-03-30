package com.example.attendancetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.attendancetrackerapp.data.Attendance
import com.example.attendancetrackerapp.data.AttendanceDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AttendanceDatabase.getInstance(application)
    private val dao = database.attendanceDao()

    val allAttendances: Flow<List<Attendance>> = dao.getAll()

    fun insert(attendance: Attendance) = viewModelScope.launch {
        dao.insert(attendance)
    }
}