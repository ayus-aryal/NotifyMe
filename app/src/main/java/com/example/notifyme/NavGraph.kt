package com.example.notifyme

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

@Composable
fun NotifyMeApp() {
    val navController = rememberNavController()
    var selectedApps by remember { mutableStateOf(setOf<String>()) } // Stores selected apps
    val appsList = AppUtils.getInstalledApps(LocalContext.current) // Fetch installed apps

    Scaffold(
        bottomBar = { BottomNavBar(navController) } // ✅ Bottom navigation bar
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "app_list",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("app_list") {
                AppListScreen(
                    apps = appsList,
                    selectedApps = selectedApps,
                    onAppToggle = { app, isSelected ->
                        selectedApps = if (isSelected) {
                            selectedApps + app.packageName
                        } else {
                            selectedApps - app.packageName
                        }
                    }
                )
            }
            composable("notifications") {
                val selectedAppInfos = appsList.filter { it.packageName in selectedApps }
                NotificationScreen(selectedAppInfos)
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Apps", Icons.Default.List, "app_list"),
        BottomNavItem("Notifications", Icons.Default.Notifications, "notifications")
    )

    NavigationBar(
        containerColor = Color.Black, // ✅ Dark theme
        contentColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)
