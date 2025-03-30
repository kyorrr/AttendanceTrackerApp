package com.example.attendancetrackerapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancetrackerapp.data.Attendance
import com.example.attendancetrackerapp.data.AttendanceDatabase
import com.example.attendancetrackerapp.ui.theme.AttendanceTheme
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") { HomeScreen(navController) }
                        composable("add") { AddAttendanceScreen(navController) }
                        composable("stats") { StatsScreen() }
                    }
                }
            }
            val database = AttendanceDatabase.getInstance(this)
            val testAttendance = Attendance(
                employeeName = "John Doe",
                entryTime = Date().time,
                exitTime = null,
                date = Date().time
            )
            lifecycleScope.launch {
                database.attendanceDao().insert(testAttendance)
                Log.d("Database", "Запись добавлена!")
            }
        }
    }
}