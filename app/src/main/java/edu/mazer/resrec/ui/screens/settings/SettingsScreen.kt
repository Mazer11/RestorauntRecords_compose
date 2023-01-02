package edu.mazer.resrec.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import edu.mazer.resrec.navigation.NavigationRoutes

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsTopAppBar(
                onBackPressed = { navController.navigate(NavigationRoutes.homeScreen.route) }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text(text = "SETTINGS SCREEN")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    onBackPressed: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back button")
            }
        },
        title = {
            Text(text = "Settings")
        }
    )
}