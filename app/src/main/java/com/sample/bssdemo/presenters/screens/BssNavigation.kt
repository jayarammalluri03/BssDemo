package com.sample.bssdemo.presenters.screens

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sample.bssdemo.R
import com.sample.bssdemo.Utils.FaceDetections
import com.sample.bssdemo.presenters.Nav.BottomBar
import com.sample.bssdemo.presenters.Nav.Route
import com.sample.bssdemo.presenters.viewmodels.EditViewModel
import com.sample.bssdemo.presenters.viewmodels.HomeViewModel
import com.sample.bssdemo.presenters.viewmodels.SavedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BssNavigation() {
    val navController= rememberNavController()

    val bottomNavItem = remember {
            listOf(
                BottomNavItems(icon = R.drawable.baseline_home_24, text = "home"),
                BottomNavItems(icon = R.drawable.baseline_save_24, text = "save")
            )
    }

    val backStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = backStackEntry?.destination?.route

    var selectedIndex = remember(key1 = backStackEntry){

        val effectiveRoute = if(currentRoute == Route.EditScreen.route)
            navController.previousBackStackEntry?.destination?.route ?: currentRoute
        else currentRoute

        when(currentRoute){
            Route.HomeScreen.route -> 0
            Route.SavedPictures.route -> 1

            else -> 0
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        }, bottomBar = {
            BottomBar(items = bottomNavItem, selected = selectedIndex, onItemSelected = {
                when(it){
                 0 -> navigateToTop(navController, Route.HomeScreen.route)
                 1 -> navigateToTop(navController, Route.SavedPictures.route)
                }
            })
        }
    ) {
        NavHost(navController= navController, startDestination = Route.HomeScreen.route, modifier = Modifier.padding(it)){
            composable(Route.HomeScreen.route) {
                val context = LocalContext.current
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(homeViewModel, onclick = { item ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("data", item)
                    navController.navigate(Route.EditScreen.route)
                })
            }

            composable(Route.SavedPictures.route) {
                val saveViewModel: SavedViewModel = hiltViewModel()
                SavedScreen(saveViewModel, onclick = { item ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("data", item)
                    navController.navigate(Route.EditScreen.route)
                })

            }

            composable(Route.EditScreen.route) { backStackEntry ->

                val data = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<FaceDetections>("data")
                val editViewModel: EditViewModel = hiltViewModel()
                LaunchedEffect(data) {
                    data?.let {
                        editViewModel.setDetection(it)
                    }
                }
                EditScreen(viewModel = editViewModel)
            }
        }
    }


}

private fun navigateToTop(navController: NavController, route: String){
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let {
            popUpTo(it){
                saveState= true
            }
            restoreState= true
            launchSingleTop = true
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    TopAppBar(title = {
        Text(text = "Bss sample")
    })
}



data class BottomNavItems(val icon: Int, val text: String)