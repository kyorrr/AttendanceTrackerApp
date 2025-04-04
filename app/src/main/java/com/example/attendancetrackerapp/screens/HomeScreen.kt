package com.example.attendancetrackerapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.attendancetrackerapp.ui.theme.ThemeProviders
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(navController: NavHostController, viewModel: AttendanceViewModel) {
    val darkTheme = ThemeProviders.DarkThemeState.current
    val user by viewModel.currentUser.collectAsState()

    if (user == null) {
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Отслеживание посещаемости",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 32.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(320.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (user!!.role == "admin") {
                Button(
                    onClick = { navController.navigate("add") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Добавить посещение")
                }

                Button(
                    onClick = { navController.navigate("manage-employees") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Дать доступ сотруднику")
                }

                Button(
                    onClick = { navController.navigate("stats") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Статистика")
                }

                Button(
                    onClick = { navController.navigate("settings") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Настройки")
                }
            } else {
                Button(
                    onClick = { navController.navigate("add") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Добавить посещение")
                }

                Button(
                    onClick = { navController.navigate("settings") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Настройки")
                }
            }

            Button(
                onClick = {
                    viewModel.setCurrentUser(null)
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Выйти")
            }
        }
    }
}