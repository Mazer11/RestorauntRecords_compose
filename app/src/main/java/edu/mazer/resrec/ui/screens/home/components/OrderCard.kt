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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.mazer.resrec.R
import edu.mazer.resrec.model.Order
import edu.mazer.resrec.ui.theme.AppTypography

@Composable
fun OrderCard(
    order: Order,
    onConfirm: () -> Unit
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
                    text = "${order.cost}" + " " + stringResource(R.string.rub),
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
            text = stringResource(R.string.note_with_dots) + " " + order.note,
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
                    Text(text = stringResource(R.string.done))
                }

                Button(
                    onClick = {
                        orderDetailsDialogState.value = orderDetailsDialogState.value.not()
                    },
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = if (orderDetailsDialogState.value)
                            stringResource(R.string.hide_details)
                        else
                            stringResource(R.string.show_details)
                    )
                }
            }
        }
    }

    if (doneConfirmationDialogState.value)
        OrderConfirmation(
            onDismiss = {
                doneConfirmationDialogState.value = doneConfirmationDialogState.value.not()
            },
            onConfirm = {
                onConfirm()
                doneConfirmationDialogState.value = doneConfirmationDialogState.value.not()
            },
            title = stringResource(R.string.confirmation)
        ) {
            Text(
                text = stringResource(R.string.are_you_sure) + " "
                        + order.table + "" + stringResource(R.string.ready_question)
            )
        }
}

@Composable
fun OrderConfirmation(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        icon = {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Confirmation icon")
        },
        title = { Text(text = title) },
        text = content
    )
}