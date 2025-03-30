package com.example.attendancetrackerapp.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancetrackerapp.screens.AddAttendanceScreen
import com.example.attendancetrackerapp.screens.HomeScreen
import com.example.attendancetrackerapp.screens.StatsScreen
import com.example.attendancetrackerapp.ui.theme.AttendanceTheme
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttendanceTheme {
                Surface {
                    val navController = rememberNavController()
                    val viewModel = AttendanceViewModel(application)
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") { HomeScreen(navController) }
                        composable("add") { AddAttendanceScreen(navController, viewModel) }
                        composable("stats") { StatsScreen() }
                    }
                }
            }
        }
    }
}