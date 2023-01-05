package edu.mazer.resrec.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.mazer.resrec.model.Dish
import edu.mazer.resrec.model.Order
import edu.mazer.resrec.navigation.NavigationRoutes
import edu.mazer.resrec.ui.screens.home.components.OrderCard

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController) {

    val testOrder = Order(
        id = "0001",
        status = "Готовится",
        time = "16:33",
        table = 4,
        cost = 785,
        dishes = mutableListOf(
            Dish(
                key = "Картофель фри",
                value = 2
            ),
            Dish(
                key = "Кола бол.",
                value = 2
            ),
            Dish(
                key = "Гамбургер",
                value = 2
            )
        ),
        waiter = "Иванов И.И.",
        note = "стандартно"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopApp(
                title = "User name",
                onIconButtonClick = {
                    navController.navigate(NavigationRoutes.settingsScreen.route)
                },
                onNavigationButtonClick = {
                    val startDestination = navController.graph.startDestinationId
                    navController.popBackStack(destinationId = startDestination, inclusive = true)
                    navController.navigate(startDestination)
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { navController.navigate(NavigationRoutes.addOrder.route) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add order", modifier = Modifier.size(64.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            OrderCard(testOrder)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopApp(
    title: String,
    onIconButtonClick: () -> Unit,
    onNavigationButtonClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        actions = {
            IconButton(
                onClick = { onIconButtonClick() }
            ) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Action button")
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onNavigationButtonClick() }
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Log out")
            }
        }
    )
}