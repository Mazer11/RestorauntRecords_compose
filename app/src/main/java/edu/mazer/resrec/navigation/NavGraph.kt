package edu.mazer.resrec.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import edu.mazer.resrec.ResRecApp
import edu.mazer.resrec.ui.screens.add_order.AddOrderScreen
import edu.mazer.resrec.ui.screens.home.HomeScreen
import edu.mazer.resrec.ui.screens.login.LoginScreen
import edu.mazer.resrec.ui.screens.settings.SettingsScreen
import edu.mazer.resrec.viewmodels.AddOrderViewModel
import edu.mazer.resrec.viewmodels.HomeViewModel
import edu.mazer.resrec.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    app: ResRecApp
) {
    val myAuth = FirebaseAuth.getInstance()
    val startDest = if (myAuth.currentUser != null)
        NavigationRoutes.HOME.route
    else
        NavigationRoutes.LOGIN.route

    NavHost(navController = navController, startDestination = startDest) {
        composable(
            route = NavigationRoutes.LOGIN.route
        ) {
            val loginVm = LoginViewModel()
            LoginScreen(navController, loginVm)
        }

        composable(
            route = NavigationRoutes.HOME.route
        ) {
            val homeVm = HomeViewModel()
            HomeScreen(navController, homeVm)
        }

        composable(
            route = NavigationRoutes.SETTINGS.route
        ) {
            SettingsScreen(
                navController,
                app
            )
        }

        composable(
            route = NavigationRoutes.ADDORDER.route
        ) {
            val dishVm = AddOrderViewModel()
            AddOrderScreen(
                navController,
                dishVm
            )
        }
    }
}