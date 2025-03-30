package com.example.attendancetrackerapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.attendancetrackerapp.data.Attendance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddAttendanceScreen(navController: NavHostController) {
    var employee by remember { mutableStateOf("") }
    var entryTime by remember { mutableStateOf(Date()) }
    var exitTime by remember { mutableStateOf<Date?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = employee,
            onValueChange = { employee = it },
            label = { Text("Сотрудник") }
        )

        // Время входа
        TextButton(
            onClick = { entryTime = Date() },
            enabled = true
        ) {
            Text(
                text = "Время входа: ${entryTime.toFormattedString()}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        //Время выхода
        TextButton(
            onClick = { exitTime = Date() },
            enabled = exitTime == null
        ) {
            Text(
                text = "Время выхода: ${exitTime?.toFormattedString() ?: "Не указано"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Кнопка сохранения
        Button(
            onClick = {
                val attendance = Attendance(
                    employeeName = employee,
                    entryTime = entryTime.time,
                    exitTime = exitTime?.time,
                    date = Date().time
                )
                // Сохранить работника через ViewModel
                navController.popBackStack()
            },
            enabled = employee.isNotBlank()
        ) {
            Text("Сохранить")
        }
    }
}

// Расширение для форматирования даты
private fun Date.toFormattedString(): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}