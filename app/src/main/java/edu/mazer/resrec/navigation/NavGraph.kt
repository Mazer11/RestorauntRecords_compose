package edu.mazer.resrec.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.mazer.resrec.ResRecApp
import edu.mazer.resrec.ui.screens.add_order.AddOrderScreen
import edu.mazer.resrec.ui.screens.home.HomeScreen
import edu.mazer.resrec.ui.screens.login.LoginScreen
import edu.mazer.resrec.ui.screens.settings.SettingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    app: ResRecApp
) {
    NavHost(navController = navController, startDestination = NavigationRoutes.loginScreen.route){
        composable(
            route = NavigationRoutes.loginScreen.route
        ){
            LoginScreen(navController)
        }

        composable(
            route = NavigationRoutes.homeScreen.route
        ){
            HomeScreen(navController)
        }

        composable(
            route = NavigationRoutes.settingsScreen.route
        ){
            SettingsScreen(
                navController,
                app
            )
        }

        composable(
            route = NavigationRoutes.addOrder.route
        ){
            AddOrderScreen(navController)
        }
    }
}