package edu.mazer.resrec.ui.screens.add_order

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import edu.mazer.resrec.R
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import edu.mazer.resrec.model.Order
import edu.mazer.resrec.ui.screens.add_order.components.AddOrderUI
import edu.mazer.resrec.ui.screens.add_order.components.DishCard
import edu.mazer.resrec.ui.screens.home.components.OrderConfirmation
import edu.mazer.resrec.viewmodels.AddOrderViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderScreen(
    navController: NavController,
    dishSearchViewModel: AddOrderViewModel
) {
    val lContext = LocalContext.current
    val dishSearchModelState by rememberFlowWithLifecycle(dishSearchViewModel.dishSearchModelState)
        .collectAsState(initial = DishSearchModelState.Empty)
    val orderContent = remember { mutableStateListOf<MenuItem>() }
    val showDetailsAlertDialog = remember { mutableStateOf(false) }
    val tableNumber = remember { mutableStateOf(0) }
    val clientNote = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val showClearButtonToTable = remember { derivedStateOf { tableNumber.value > 0 } }
    val showClearButtonToNote = remember { derivedStateOf { clientNote.value.isNotEmpty() } }
    val isOrderFormCorrect = remember {
        derivedStateOf {
            clientNote.value.isNotEmpty() &&
                    tableNumber.value > 0
        }
    }

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
            onConfirm = {
                if (isOrderFormCorrect.value) {
                    dishSearchViewModel.onConfirmOrder(
                        orderContent,
                        tableNumber.value,
                        clientNote.value
                    )
                    showDetailsAlertDialog.value = showDetailsAlertDialog.value.not()
                } else {
                    Toast.makeText(lContext, "Note/Table is wrong", Toast.LENGTH_SHORT).show()
                }
            }) {

            LazyColumn() {
                item {
                    //Table
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Номер столика")

                        OutlinedTextField(
                            value = tableNumber.value.toString(),
                            singleLine = true,
                            maxLines = 1,
                            isError = showClearButtonToTable.value.not(),
                            onValueChange = { tableNumber.value = it.toInt() },
                            placeholder = { Text(text = "Номер столика") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            ),
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = showClearButtonToTable.value,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { tableNumber.value = 0 }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }

                    //Note
                    OutlinedTextField(
                        value = clientNote.value,
                        singleLine = true,
                        maxLines = 1,
                        isError = showClearButtonToNote.value.not(),
                        onValueChange = { clientNote.value = it },
                        placeholder = { Text(text = "Примечание") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = showClearButtonToNote.value,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(onClick = { clientNote.value = "" }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "")
                                }
                            }
                        },
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
                items(orderContent) { dish ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${dish.key} - ${dish.menuItemValues.cost}" + stringResource(id = R.string.rub))
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