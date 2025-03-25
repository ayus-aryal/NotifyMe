package com.example.notifyme

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

// Define your own AppInfo data class
data class AppInfo(
    val name: String,
    val packageName: String,
    val icon: Drawable
)

object AppUtils {
    fun getInstalledApps(context: Context): List<AppInfo> {
        val packageManager: PackageManager = context.packageManager
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Exclude system apps
            .map {
                AppInfo(
                    name = it.loadLabel(packageManager).toString(),
                    packageName = it.packageName,
                    icon = it.loadIcon(packageManager)
                )
            }
    }
}
