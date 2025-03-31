package com.example.attendancetrackerapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.attendancetrackerapp.viewmodel.AttendanceViewModel
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AttendanceViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = login.value,
            onValueChange = { login.value = it },
            label = { Text("Логин") }
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                coroutineScope.launch {
                    val employee = viewModel.authenticate(login.value, password.value)
                    Log.d("LoginScreen", "Auth result: ${employee?.name}")
                    if (employee != null) {
                        viewModel.setCurrentUser(employee)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Войти")
        }
    }
}