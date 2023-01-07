package edu.mazer.resrec.ui.screens.add_order

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import edu.mazer.resrec.ui.screens.add_order.components.AddOrderUI
import edu.mazer.resrec.ui.screens.add_order.components.DishCard
import edu.mazer.resrec.viewmodels.DishSearchViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderScreen(
    navController: NavController,
    dishSearchViewModel: DishSearchViewModel
) {
    val dishSearchModelState by rememberFlowWithLifecycle(dishSearchViewModel.dishSearchModelState)
        .collectAsState(initial = DishSearchModelState.Empty)
    val orderContent = remember { mutableStateListOf<MenuItem>() }

    AddOrderUI(
        searchText = dishSearchModelState.searchText,
        matchesFound = dishSearchModelState.dishes.isNotEmpty(),
        placeholderText = "Поиск",
        onSearchTextChanged = { dishSearchViewModel.onSearchTextChanged(it) },
        onClearClick = { dishSearchViewModel.onClearClick() },
        onNavigateBack = { navController.navigateUp() },
        floatingAB = {
            BadgedBox(
                badge = {
                    Badge {
                        Text(text = orderContent.size.toString())
                    }
                }
            ) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Состав заказа") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = ""
                        )
                    },
                    onClick = { /*TODO*/ })
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(dishSearchModelState.dishes) { dish ->
                DishCard(
                    dish = dish,
                    onPriceClick = { orderContent.add(dish) }
                )
            }
        }
    }
}

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun buttonWithBadge() {

    BadgedBox(
        badge = {
            Badge {
                Text(text = "3")
            }
        }
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Состав заказа") },
            icon = { Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "") },
            onClick = { /*TODO*/ })
    }
}