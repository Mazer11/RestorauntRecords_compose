package edu.mazer.resrec.ui.screens.add_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import edu.mazer.resrec.R
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import edu.mazer.resrec.ui.screens.add_order.components.AddOrderUI
import edu.mazer.resrec.ui.screens.add_order.components.DishCard
import edu.mazer.resrec.ui.screens.home.components.OrderConfirmation
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
    val showDetailsAlertDialog = remember { mutableStateOf(false) }

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
                    onClick = { showDetailsAlertDialog.value = showDetailsAlertDialog.value.not() })
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

    if (showDetailsAlertDialog.value)
        OrderConfirmation(
            title = "Детали заказа",
            onDismiss = { showDetailsAlertDialog.value = showDetailsAlertDialog.value.not() },
            onConfirm = { /*TODO*/ }) {
            LazyColumn(){
                items(orderContent){ dish ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${dish.key} - ${dish.cost}" + stringResource(id = R.string.rub))
                        IconButton(onClick = { orderContent.remove(dish) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove dish"
                            )
                        }
                    }
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