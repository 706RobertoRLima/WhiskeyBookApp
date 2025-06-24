package com.whiskeybook

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search") }
                )
                IconButton(
                    onClick = {
                        if (searchQuery.isNotBlank()) {
                            val query = searchQuery.replace(" ", "+")
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://www.google.com/search?q=$query")
                            }
                            context.startActivity(intent)
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search on Google")
                }
            }
        }

        // to add new whiskey element to the list
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(modifier = Modifier.weight(1f),
                    value = newWhiskey,
                    onValueChange = { newWhiskey = it },
                    label = { Text("Add Whiskey") })

                Button(onClick = {
                    if (newWhiskey.isNotBlank()) {
                        whiskeys = whiskeys + newWhiskey
                        newWhiskey = ""
                    }
                }) {
                    Text("Add")
                }
            }
        }


        Text("Whiskey List", fontSize = 24.sp, fontWeight = FontWeight.Bold)

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