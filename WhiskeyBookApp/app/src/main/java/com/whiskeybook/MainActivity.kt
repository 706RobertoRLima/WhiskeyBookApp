package com.whiskeybook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

import androidx.compose.runtime.saveable.rememberSaveable
import db.User

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
    var loggedInUser by remember { mutableStateOf<User?>(null) }
    var showSignUp by rememberSaveable { mutableStateOf(false) }

    if (loggedIn && loggedInUser != null) {WhiskeyListScreen(
        user = loggedInUser!!,
        onLogout = {
            loggedInUser = null
            loggedIn = false
        }
    )
    } else {
        if (showSignUp) {
            SignUpScreen(
                onSignUpSuccess = { newUser ->
                    showSignUp = false
                    loggedInUser = newUser
                    loggedIn = true
                }
            )
        } else {
            LoginScreen(
                onLoginSuccess = { user ->
                    loggedInUser = user
                    loggedIn = user != null
                },
                onSignUpClick = {
                    showSignUp = true
                }
            )
        }
    }
}