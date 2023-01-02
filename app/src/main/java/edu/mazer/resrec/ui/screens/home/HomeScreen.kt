package edu.mazer.resrec.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                }
            )
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
    onIconButtonClick: () -> Unit
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
        }
    )
}