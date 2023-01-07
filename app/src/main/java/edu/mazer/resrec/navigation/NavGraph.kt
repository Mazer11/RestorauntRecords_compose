package edu.mazer.resrec.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import edu.mazer.resrec.ResRecApp
import edu.mazer.resrec.ui.screens.add_order.AddOrderScreen
import edu.mazer.resrec.ui.screens.home.HomeScreen
import edu.mazer.resrec.ui.screens.login.LoginScreen
import edu.mazer.resrec.ui.screens.settings.SettingsScreen
import edu.mazer.resrec.viewmodels.DishSearchViewModel
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
        NavigationRoutes.homeScreen.route
    else
        NavigationRoutes.loginScreen.route

    NavHost(navController = navController, startDestination = startDest) {
        composable(
            route = NavigationRoutes.loginScreen.route
        ) {
            val loginVm = LoginViewModel()
            LoginScreen(navController, loginVm)
        }

        composable(
            route = NavigationRoutes.homeScreen.route
        ) {
            val homeVm = HomeViewModel()
            HomeScreen(navController, homeVm)
        }

        composable(
            route = NavigationRoutes.settingsScreen.route
        ) {
            SettingsScreen(
                navController,
                app
            )
        }

        composable(
            route = NavigationRoutes.addOrder.route
        ) {
            val dishVm = DishSearchViewModel()
            AddOrderScreen(
                navController,
                dishVm
            )
        }
    }
}

fun checkAuthState(
    navController: NavController,
    auth: FirebaseAuth
) {

}