package com.sample.bssdemo.presenters.Nav

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.bssdemo.presenters.screens.BssNavigation
import com.sample.bssdemo.presenters.screens.PermissionScreen


@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

    NavHost(
        navController = navController,
        startDestination = "launcher"
    ) {

        composable("launcher") {

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { granted ->
                if (granted) {
                    navController.navigate(Route.MenuScreen.route) {
                        popUpTo("launcher") { inclusive = true }
                    }
                } else {
                    navController.navigate(Route.PermissionScreen.route) {
                        popUpTo("launcher") { inclusive = true }
                    }
                }
            }

            LaunchedEffect(Unit) {
                val granted = ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED

                if (granted) {
                    navController.navigate(Route.MenuScreen.route) {
                        popUpTo("launcher") { inclusive = true }
                    }
                } else {
                    permissionLauncher.launch(permission)
                }
            }
        }

        composable(Route.MenuScreen.route) {
            BssNavigation()
        }

        composable(Route.PermissionScreen.route) {
            PermissionScreen(navController)
        }
    }
}