package edu.mazer.resrec.ui.screens.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ResRecTheme
import edu.mazer.resrec.model.Dish
import edu.mazer.resrec.model.Order
import edu.mazer.resrec.ui.theme.AppTypography

@Composable
fun OrderCard(
    order: Order
) {

    val doneConfirmationDialogState = remember { mutableStateOf(false) }
    val orderDetailsDialogState = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(durationMillis = 300)
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .padding(2.dp)
            ) {
                Text(
                    text = order.table.toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    style = AppTypography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = order.time,
                    style = AppTypography.titleLarge
                )
                Text(
                    text = "${order.cost} руб.",
                    style = AppTypography.titleLarge
                )
            }
            if (order.status == "Готовится")
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Order status",
                    tint = MaterialTheme.colorScheme.onError,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.error,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                )
            else if (order.status == "Готов к выдаче")
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Order is ready",
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                )
        }
        Text(
            text = "Примечание: ${order.note}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = AppTypography.bodyMedium
        )

        if (orderDetailsDialogState.value) {
            order.dishes.forEach { dish ->
                Text(
                    text = "${dish.key} x ${dish.value}",
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                )
                Divider(
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(
                        top = 2.dp,
                        bottom = 4.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {

                OutlinedButton(
                    onClick = {
                        doneConfirmationDialogState.value = doneConfirmationDialogState.value.not()
                    },
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Text(text = "Готово")
                }

                Button(
                    onClick = {
                        orderDetailsDialogState.value = orderDetailsDialogState.value.not()
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = if (orderDetailsDialogState.value)
                            "Свернуть"
                        else
                            "Раскрыть"
                    )
                }
            }
        }
    }

    if (doneConfirmationDialogState.value)
        OrderConfirmation(
            orderId = order.id,
            onDismiss = {
                doneConfirmationDialogState.value = doneConfirmationDialogState.value.not()
            },
            onConfirm = {}
        )
}

@Composable
fun OrderConfirmation(
    orderId: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        },
        icon = {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Confirmation icon")
        },
        title = {
            Text(text = "Confirmation")
        },
        text = {
            Text(text = "Are you sure that order №$orderId is done?")
        }
    )
}

@Preview
@Composable
fun OrderCardPrev() {

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
        note = "Стандартно"
    )

    ResRecTheme() {
        OrderCard(order = testOrder)
    }
}









