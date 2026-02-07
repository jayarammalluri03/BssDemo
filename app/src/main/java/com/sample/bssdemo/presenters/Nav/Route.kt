package com.sample.bssdemo.presenters.Nav

sealed class Route(val route: String) {

    object MenuScreen: Route(route = "menuScreen")
    object PermissionScreen: Route(route = "permissionScreen")
    object HomeScreen: Route(route = "homeScreen")
    object SavedPictures: Route(route = "savedPictureScreen")

    object EditScreen: Route(route = "EditScreen")
}