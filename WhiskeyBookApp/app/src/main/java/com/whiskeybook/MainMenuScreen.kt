package com.whiskeybook
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import db.*

@Composable
fun MainMenuScreen(user: User,
                   onLogout: () -> Unit,
                   onNavigateToWhiskeys: () -> Unit,
                   onNavigateToUserInfo: () -> Unit) {
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
            IconButton(
                onClick = onLogout
            ) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // My Collection Button
            ClickableImageWithLabel(
                imageRes = R.drawable.ic_collection,
                label = "My Collection",
                onClick = onNavigateToWhiskeys
            )

            Spacer(modifier = Modifier.height(32.dp))

            // My Info Button
            ClickableImageWithLabel(
                imageRes = R.drawable.ic_user_info,
                label = "My Info",
                onClick = onNavigateToUserInfo
            )
        }
    }
}

@Composable
fun ClickableImageWithLabel(imageRes: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = label,
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

