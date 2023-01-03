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
import edu.mazer.resrec.navigation.NavigationRoutes

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController) {
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
            Text(text = "HOME SCREEN")
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