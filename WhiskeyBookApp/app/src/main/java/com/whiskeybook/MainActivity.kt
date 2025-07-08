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
    var currentScreen by remember { mutableStateOf("menu") } // "menu", "whiskeys", "userinfo"

    when {
        loggedIn && loggedInUser != null -> {
            when (currentScreen) {
                "menu" -> {
                    MainMenuScreen(
                        user = loggedInUser!!,
                        onLogout = {
                            loggedIn = false
                            loggedInUser = null
                            currentScreen = "menu"
                        },
                        onNavigateToWhiskeys = { currentScreen = "whiskeys" },
                        onNavigateToUserInfo = { currentScreen = "userinfo" }
                    )
                }

                "whiskeys" -> {
                    WhiskeyListScreen(
                        user = loggedInUser!!,
                        onLogout = {
                            loggedIn = false
                            loggedInUser = null
                            currentScreen = "menu"
                        }
                    )
                }

                "userinfo" -> {
                    UserInfoScreen(
                        user = loggedInUser!!,
                        onBack = { currentScreen = "menu" },
                        onLogout = {
                            loggedIn = false
                            loggedInUser = null
                            currentScreen = "menu"
                        }
                    )
                }
            }
        }

        showSignUp -> {
            SignUpScreen(
                onSignUpSuccess = { newUser ->
                    showSignUp = false
                    loggedInUser = newUser
                    loggedIn = true
                    currentScreen = "menu"
                }
            )
        }

        else -> {
            LoginScreen(
                onLoginSuccess = { user ->
                    loggedInUser = user
                    loggedIn = user != null
                    currentScreen = "menu"
                },
                onSignUpClick = {
                    showSignUp = true
                }
            )
        }
    }
}
