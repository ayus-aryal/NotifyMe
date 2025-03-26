package com.example.notifyme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppListScreen(apps: List<AppInfo>, onAppClick: (AppInfo) -> Unit) {
    val selectedApps = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp, bottom = 30.dp)
            .background(Color.Black)

    ) {
        items(apps) { app ->
            AppCard(
                app = app,
                isSelected = selectedApps[app.packageName] ?: false,
                onSelectionChange = { isSelected ->
                    selectedApps[app.packageName] = isSelected
                },
                onAppClick = onAppClick
            )
        }
    }
}

@Composable
fun AppCard(
    app: AppInfo,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    onAppClick: (AppInfo) -> Unit

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically // Aligns items vertically in the center

        ) {
            // Checkbox
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChange,
                modifier = Modifier.padding(end = 8.dp)
            )

            // App Icon
            Image(
                bitmap = app.icon.toBitmap().asImageBitmap(),
                contentDescription = app.name,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )

            // App Name
            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

