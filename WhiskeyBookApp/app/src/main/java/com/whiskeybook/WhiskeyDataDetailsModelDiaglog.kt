package com.whiskeybook

import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WhiskeyDetailScreen(whiskey: Whiskey, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            TextButton(onClick = onClose) {
                Text("Close")
            }
        },
        title = { Text(whiskey.whiskeyName) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Show image if present
                whiskey.imageUri?.let {
                    AsyncImage(
                        model = whiskey.imageUri,
                        contentDescription = "Whiskey Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Text("Age: ${whiskey.age} years")
                Text("Alcohol: ${whiskey.alcoholPercentage}%")
                Text("Notes: ${whiskey.notes}")
            }
        }
    )
}

