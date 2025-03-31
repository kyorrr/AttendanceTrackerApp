package com.example.attendancetrackerapp.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.attendancetrackerapp.ui.theme.ThemeProviders

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EmployeeManagementScreen(navController: NavHostController, viewModel: AttendanceViewModel) {
    val darkTheme = ThemeProviders.DarkThemeState.current
    val employees by viewModel.employees.collectAsState(initial = emptyList())

    val user by viewModel.currentUser.collectAsState()
    if (user == null || user!!.role != "admin") {
        navController.popBackStack()
        navController.navigate("home")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Дать доступ сотруднику",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp, top = 12.dp)
        )

        var newName by remember { mutableStateOf("") }
        var newPosition by remember { mutableStateOf("") }
        var newLogin by remember { mutableStateOf("") }
        var newPassword by remember { mutableStateOf("") }
        var newRole by remember { mutableStateOf("employee") }

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

        OutlinedTextField(
            value = newLogin,
            onValueChange = { newLogin = it },
            label = { Text("Логин") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp)
        )

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 16.dp)
        )

        var roleExpanded by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Роль:", modifier = Modifier.weight(1f))
            DropdownMenu(
                expanded = roleExpanded,
                onDismissRequest = { roleExpanded = false }
            ) {
                listOf("employee", "admin").forEach { role ->
                    DropdownMenuItem(
                        text = { Text(role) },
                        onClick = {
                            newRole = role
                            roleExpanded = false
                        }
                    )
                }
            }
            Button(
                onClick = { roleExpanded = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(newRole.uppercase())
            }
        }

        Button(
            onClick = {
                val newEmployee = Employee(
                    id = 0,
                    name = newName,
                    position = newPosition,
                    login = newLogin,
                    password = newPassword,
                    role = newRole
                )
                viewModel.insertEmployee(newEmployee)
                newName = ""
                newPosition = ""
                newLogin = ""
                newPassword = ""
                newRole = "employee"
            },
            enabled = newName.isNotBlank() && newLogin.isNotBlank() && newPassword.isNotBlank(),
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