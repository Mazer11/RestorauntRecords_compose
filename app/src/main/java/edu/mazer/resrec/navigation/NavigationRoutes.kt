package edu.mazer.resrec.navigation

sealed class NavigationRoutes(
    val route: String
){

    object loginScreen: NavigationRoutes( route = "login" )
    object homeScreen: NavigationRoutes( route = "home" )
    object settingsScreen: NavigationRoutes( route = "settings" )

}
