package com.example.attendancetrackerapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.attendancetrackerapp.data.Attendance
import com.example.attendancetrackerapp.ui.theme.ThemeProviders
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddAttendanceScreen(navController: NavHostController, viewModel: AttendanceViewModel) {
    val darkTheme = ThemeProviders.DarkThemeState.current

    val employees by viewModel.employees.collectAsState()

    var selectedEmployee by remember { mutableStateOf(employees.firstOrNull()) }
    var entryTime by remember { mutableStateOf(Date()) }
    var exitTime by remember { mutableStateOf<Date?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = selectedEmployee?.name ?: "Выберите сотрудника",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(8.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                employees.forEach { employee ->
                    DropdownMenuItem(
                        text = { Text(employee.name) },
                        onClick = {
                            selectedEmployee = employee
                            expanded = false
                        }
                    )
                }
            }
        }

        TextButton(
            onClick = { entryTime = Date() },
            enabled = true
        ) {
            Text(text = "Время входа: ${entryTime.toFormattedString()}", color = MaterialTheme.colorScheme.onBackground)
        }

        TextButton(
            onClick = { exitTime = Date() },
            enabled = exitTime == null
        ) {
            Text(text = "Время выхода: ${exitTime?.toFormattedString() ?: "Не указано"}", color = MaterialTheme.colorScheme.onBackground)
        }

        Button(
            onClick = {
                val attendance = Attendance(
                    employeeId = selectedEmployee?.id ?: 0,
                    entryTime = entryTime.time,
                    exitTime = exitTime?.time,
                    date = Date().time
                )
                viewModel.insert(attendance)
                navController.popBackStack()
            },
            enabled = selectedEmployee != null
        ) {
            Text("Сохранить")
        }
    }
}


private fun Date.toFormattedString(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}