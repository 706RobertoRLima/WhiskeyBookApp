package com.whiskeybook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

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
