package com.whiskeybook

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import db.*
@Composable
fun WhiskeyListScreen(
    user: User,
    onLogout: () -> Unit,
    viewModel: WhiskeyViewModel = viewModel()
) {
    var newWhiskeyName by remember { mutableStateOf("") }
    var whiskeys by remember { mutableStateOf(user.whiskeyList.toList()) }
    var showDetail by remember { mutableStateOf(false) }
    var selectedWhiskey by remember { mutableStateOf<Whiskey?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Top-right user info + logout
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Hello, ${user.userName}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp, start = 32.dp, end = 32.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var query by remember { mutableStateOf("") }
            var searchPressed by remember { mutableStateOf(false) }

            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Search Whiskey") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            viewModel.search(query)
                            searchPressed = true
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }

                Spacer(Modifier.height(16.dp))

                val whiskeysSearched = viewModel.whiskeyList
                if (searchPressed && whiskeysSearched.isEmpty()) {
                    Text("No results found.")
                } else {
                    LazyColumn {
                        items(whiskeysSearched) { whiskey ->
                            Text(whiskey.whiskeyName)
                            Divider()
                        }
                    }
                }
            }

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
                        value = newWhiskeyName,
                        onValueChange = { newWhiskeyName = it },
                        label = { Text("Add Whiskey") }
                    )

                    Button(onClick = {
                        if (newWhiskeyName.isNotBlank()) {
                            whiskeys = whiskeys + Whiskey(
                                newWhiskeyName,
                                "single",
                                "scotland",
                                12,
                                40,
                                "",
                                ""
                            )
                            newWhiskeyName = ""
                        }
                    }) {
                        Text("Add")
                    }
                }
            }

            Text("My Whiskey List", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            whiskeys.forEach { whiskey ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        selectedWhiskey = whiskey
                        showDetail = true
                    }) {
                        Text(whiskey.whiskeyName)
                    }
                    IconButton(onClick = {
                        whiskeys = whiskeys.filterNot { it == whiskey }
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }

        if (showDetail && selectedWhiskey != null) {
            WhiskeyDetailScreen(whiskey = selectedWhiskey!!) {
                showDetail = false
            }
        }
    }
}
