package com.whiskeybook

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

    var newWhiskeyName by remember { mutableStateOf("") }
    var whiskeys by remember { mutableStateOf(listOf(   Whiskey("Glenlivet",0,40,""),
                                                        Whiskey("Macallan",12,40, "Rich, smooth, and balanced with notes of honey, citrus, and ginger.", "https://www.garcias.pt/ficheiros/dinamicos/multimedia/imagem/produtos/whisky/__fmhidden__9426f628990414b19a00891c62c5ca9b/0595cab08cecf686d42da15b34549047.jpg"))) }

    var showDetail by remember { mutableStateOf(false) }
    var selectedWhiskey by remember { mutableStateOf<Whiskey?>(null) }

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
                                data = Uri.parse("https://www.whiskybase.com/search/?q=$query")
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
                    value = newWhiskeyName,
                    onValueChange = { newWhiskeyName = it },
                    label = { Text("Add Whiskey") })

                Button(onClick = {
                    if (newWhiskeyName.isNotBlank()) {
                        whiskeys = whiskeys + Whiskey(newWhiskeyName,0,0,"")
                        newWhiskeyName = ""
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