package edu.mazer.resrec.navigation

sealed class NavigationRoutes(
    val route: String
) {

    object LOGIN : NavigationRoutes(route = "login")
    object HOME : NavigationRoutes(route = "home")
    object SETTINGS : NavigationRoutes(route = "settings")
    object ADDORDER : NavigationRoutes(route = "add_order")

}
