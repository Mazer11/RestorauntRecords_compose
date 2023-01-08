package edu.mazer.resrec.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.mazer.resrec.navigation.NavigationRoutes
import edu.mazer.resrec.ui.screens.home.components.OrderCard
import edu.mazer.resrec.viewmodels.HomeViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val currentOrders = viewModel.currentOrders.observeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopApp(
                title = "Имя пользователя",
                onIconButtonClick = {
                    navController.navigate(NavigationRoutes.settingsScreen.route)
                },
                onNavigationButtonClick = {
                    viewModel.signOut()
                    navController.popBackStack()
                    navController.navigate(NavigationRoutes.loginScreen.route)
                }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { navController.navigate(NavigationRoutes.addOrder.route) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add order",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {
            Log.e("Orders", "LazyColumn()")
            if (currentOrders.value != null)
                items(currentOrders.value!!) { orderWithId ->
                    val order = orderWithId.order
                    val id = orderWithId.id
                    OrderCard(
                        order = order!!,
                        onConfirm = {
                            viewModel.completeOrder(order, id!!)
                        }
                    )
                }
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