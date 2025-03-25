package com.example.notifyme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppListScreen(apps: List<AppInfo>, onAppClick: (AppInfo) -> Unit) {
    LazyColumn {
        items(apps) { app ->
            AppCard(app, onAppClick)
        }
    }
}

@Composable
fun AppCard(app: AppInfo, onAppClick: (AppInfo) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAppClick(app) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                bitmap = app.icon.toBitmap().asImageBitmap(),
                contentDescription = app.name,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = app.name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
