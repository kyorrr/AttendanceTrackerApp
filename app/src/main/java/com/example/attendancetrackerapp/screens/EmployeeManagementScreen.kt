package com.example.attendancetrackerapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.attendancetrackerapp.data.Employee
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import com.example.attendancetrackerapp.ui.theme.ThemeProviders

@Composable
fun EmployeeManagementScreen(navController: NavHostController, viewModel: AttendanceViewModel) {
    val darkTheme = ThemeProviders.DarkThemeState.current
    val employees by viewModel.employees.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var newName by remember { mutableStateOf("") }
        var newPosition by remember { mutableStateOf("") }

        Text(
            text = "Дать доступ сотруднику",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp, top = 12.dp)
        )

        OutlinedTextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text("Имя сотрудника") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp)
        )

        OutlinedTextField(
            value = newPosition,
            onValueChange = { newPosition = it },
            label = { Text("Должность") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp)
        )

        Button(
            onClick = {
                val newEmployee = Employee(
                    id = 0,
                    name = newName,
                    position = newPosition
                )
                viewModel.insertEmployee(newEmployee)
                newName = ""
                newPosition = ""
            },
            enabled = newName.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp)
        ) {
            Text("Дать доступ")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            items(employees) { employee ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${employee.name} (${employee.position})",
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { viewModel.deleteEmployee(employee) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Отозвать доступ", color = MaterialTheme.colorScheme.onError)
                    }
                }
            }
        }
    }
}