package com.example.notifyme

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import android.app.AppOpsManager
import android.content.Intent
import android.os.Build
import android.provider.Settings



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        if (!hasUsageStatsPermission(this)) {
            // Permission is granted - Proceed with app functionality.
            requestUsageStatsPermission(this)
        } else {
            // Permission is not granted - Next step will request it.
        }

        setContent {

        }
    }


    // Function to check if permission is granted.
    private fun hasUsageStatsPermission(context: Context):Boolean{
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            appOps.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        }
        else{
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }


    private fun requestUsageStatsPermission(context: Context) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        context.startActivity(intent)
    }
}








