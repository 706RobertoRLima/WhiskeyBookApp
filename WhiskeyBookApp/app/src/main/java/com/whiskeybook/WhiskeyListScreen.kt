package com.whiskeybook

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight


@Composable
fun WhiskeyListScreen() {

    var newWhiskey by remember { mutableStateOf("") }
    var whiskeys by remember { mutableStateOf(listOf("Glenlivet", "Macallan")) }
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Search on google
        Text("Search Whiskey on Google", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (searchQuery.isNotBlank()) {
                val query = searchQuery.replace(" ", "+")
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://www.google.com/search?q=$query")
                }
                context.startActivity(intent)
            }
        }) {
            Text("Search on Google")
        }
        Spacer(modifier = Modifier.height(24.dp))

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