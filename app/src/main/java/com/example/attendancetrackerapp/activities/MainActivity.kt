package com.example.attendancetrackerapp.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.attendancetrackerapp.screens.AddAttendanceScreen
import com.example.attendancetrackerapp.screens.EmployeeManagementScreen
import com.example.attendancetrackerapp.screens.HomeScreen
import com.example.attendancetrackerapp.screens.LoginScreen
import com.example.attendancetrackerapp.screens.SettingsScreen
import com.example.attendancetrackerapp.screens.StatsScreen
import com.example.attendancetrackerapp.ui.theme.AttendanceTheme
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AttendanceViewModel by viewModels()
            val darkTheme by viewModel.darkTheme.collectAsState()

            AttendanceTheme(darkTheme = darkTheme) {
                Surface {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") { LoginScreen(navController, viewModel) }
                        composable("home") { HomeScreen(navController, viewModel) }
                        composable("add") { AddAttendanceScreen(navController, viewModel) }
                        composable("stats") { StatsScreen(navController, viewModel) }
                        composable("settings") { SettingsScreen(navController, viewModel) }
                        composable("manage-employees") { EmployeeManagementScreen(navController, viewModel) }
                    }
                }
            }
        }
    }
}