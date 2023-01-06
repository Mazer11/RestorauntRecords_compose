package edu.mazer.resrec.ui.screens.add_order

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.ui.screens.add_order.components.AddOrderUI
import edu.mazer.resrec.ui.screens.add_order.components.DishCard
import edu.mazer.resrec.viewmodels.DishSearchViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun AddOrderScreen(
    navController: NavController,
    dishSearchViewModel: DishSearchViewModel
) {
    val dishSearchModelState by rememberFlowWithLifecycle(dishSearchViewModel.dishSearchModelState)
        .collectAsState(initial = DishSearchModelState.Empty)

    AddOrderUI(
        searchText = dishSearchModelState.searchText,
        matchesFound = dishSearchModelState.dishes.isNotEmpty(),
        placeholderText = "Поиск",
        onSearchTextChanged = { dishSearchViewModel.onSearchTextChanged(it) },
        onClearClick = { dishSearchViewModel.onClearClick() },
        onNavigateBack = { navController.navigateUp() }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(dishSearchModelState.dishes){dish ->
                DishCard(dish = dish)
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