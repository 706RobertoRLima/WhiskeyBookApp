package com.example.whiskeybookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var loggedIn by remember { mutableStateOf(false) }
            if (loggedIn) {
                WhiskeyListScreen()
            } else {
                LoginScreen { success -> loggedIn = success }
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: (Boolean) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("User Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        if (error) {
            Text("Invalid credentials", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            error = !(email == "robertorl_19@hotmail.com" && password == "1234")
            onLoginSuccess(!error)
        }) {
            Text("Login")
        }
    }
}

@Composable
fun WhiskeyListScreen() {

    var newWhiskey by remember { mutableStateOf("") }
    var whiskeys by remember { mutableStateOf(listOf("Glenlivet", "Macallan")) }


    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // to add new whiskey element to the list
        Text("Whiskey List", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = newWhiskey, onValueChange = { newWhiskey = it }, label = { Text("Add Whiskey") })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (newWhiskey.isNotBlank()) {
                whiskeys = whiskeys + newWhiskey
                newWhiskey = ""
            }
        }) {
            Text("Add")
        }

        // to remove whiskey element from the list
        whiskeys.forEach { whiskey ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("– $whiskey")
                Button(onClick = {
                    whiskeys = whiskeys.filterNot { it == whiskey }
                }) {
                    Text("Remover")
                }
            }
        }
    }
}
