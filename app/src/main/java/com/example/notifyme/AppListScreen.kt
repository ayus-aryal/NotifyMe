package com.example.notifyme

import android.graphics.drawable.Drawable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap

@Composable
fun AppListScreen(apps: List<AppInfo>, selectedApps: Set<String>, onAppToggle: (AppInfo, Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Dark theme support
            .padding(16.dp)
    ) {
        // Title centered
        Text(
            text = "Manage Notifications",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.White, // Dark theme text color
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 20.dp)
                .wrapContentWidth(Alignment.CenterHorizontally) // Center title
        )

        if (selectedApps.isNotEmpty()) {
            SectionTitle("Selected Apps")
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(apps.filter { selectedApps.contains(it.packageName) }) { app ->
                    AppCard(
                        app = app,
                        isSelected = true,
                        onSelectionChange = { onAppToggle(app, false) }
                    )
                }
            }
        }

        SectionTitle("Available Apps")
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(apps.filter { !selectedApps.contains(it.packageName) }) { app ->
                AppCard(
                    app = app,
                    isSelected = false,
                    onSelectionChange = { onAppToggle(app, true) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AppCard(
    app: AppInfo,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectionChange(!isSelected) }
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Dark theme support
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = onSelectionChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(end = 16.dp)
            )

            Image(
                bitmap = app.icon.toBitmap().asImageBitmap(),
                contentDescription = app.name,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.primary, // Better visibility in dark mode
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}
