package edu.mazer.resrec.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.mazer.resrec.ui.screens.home.HomeScreen
import edu.mazer.resrec.ui.screens.login.LoginScreen
import edu.mazer.resrec.ui.screens.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NavigationRoutes.loginScreen.route){
        composable(
            route = NavigationRoutes.loginScreen.route
        ){
            LoginScreen()
        }

        composable(
            route = NavigationRoutes.homeScreen.route
        ){
            HomeScreen()
        }

        composable(
            route = NavigationRoutes.settingsScreen.route
        ){
            SettingsScreen()
        }
    }
}