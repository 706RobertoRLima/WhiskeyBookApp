package com.whiskeybook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import db.UserDataManager

@Composable
fun LoginScreen(
    context: Context = LocalContext.current,
    onLoginSuccess: (Boolean) -> Unit,
    onSignUpClick: () -> Unit
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("User Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = userId, onValueChange = { userId = it }, label = { Text("User Id") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            error = !(UserDataManager.validateCredentials(context, userId, password))
            onLoginSuccess(!error)
        }) {
            Text("Login")
        }
        if (error) {
            Text("Invalid credentials", color = MaterialTheme.colorScheme.error)
        }
        TextButton(onClick = onSignUpClick) {
            Text("Sign Up")
        }
    }
}

@Composable
fun SignUpScreen(onSignUpSuccess: () -> Unit, context: Context = LocalContext.current ) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var userMail by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = userId, onValueChange = { userId = it }, label = { Text("User ID") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = userName, onValueChange = { userName = it }, label = { Text("Full Name") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = userMail, onValueChange = { userMail = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            UserDataManager.createUser(context, userId, password, userName, userMail, emptyList<Whiskey>())
            onSignUpSuccess()
        }) {
            Text("Create Account")
        }
    }
}
