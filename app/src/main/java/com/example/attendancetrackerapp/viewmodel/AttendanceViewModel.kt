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

    private val _currentUser = MutableStateFlow<Employee?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _darkTheme = MutableStateFlow(false)
    val darkTheme = _darkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            val savedUser = getSavedUser()
            _currentUser.value = savedUser
        }
    }

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

    suspend fun authenticate(login: String, password: String): Employee? {
        return employeeDao.findEmployee(login, password)
    }

    fun setCurrentUser(employee: Employee?) {
        _currentUser.value = employee
        saveUser(employee)
    }

    fun isUserAdmin(): Boolean {
        return _currentUser.value?.role == "admin"
    }

    private fun saveUser(employee: Employee?) {
        val prefs = getApplication<Application>().getSharedPreferences("user_prefs", 0).edit()
        if (employee == null) {
            prefs.remove("current_user_id").apply()
        } else {
            prefs.putString("current_user_id", employee.id.toString()).apply()
        }
    }

    private suspend fun getSavedUser(): Employee? {
        val prefs = getApplication<Application>().getSharedPreferences("user_prefs", 0)
        val savedUserId = prefs.getString("current_user_id", null) ?: return null
        return employeeDao.findById(savedUserId.toInt())
    }
}