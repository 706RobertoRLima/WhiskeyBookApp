package com.whiskeybook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                MainApp()
        }
    }
}

@Composable
fun MainApp() {
    var loggedIn by rememberSaveable { mutableStateOf(false) }
    var showSignUp by rememberSaveable { mutableStateOf(false) }

    if (loggedIn) {
        // Main content after login
        WhiskeyListScreen()
    } else {
        if (showSignUp) {
            SignUpScreen(
                onSignUpSuccess = {
                    showSignUp = false
                    loggedIn = true
                }
            )
        } else {
            LoginScreen(
                onLoginSuccess = { success ->
                    loggedIn = success
                },
                onSignUpClick = {
                    showSignUp = true
                }
            )
        }
    }
}