package edu.mazer.resrec.ui.screens.add_order.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.mazer.resrec.model.MenuItem
import edu.mazer.resrec.R

@Composable
fun DishCard(
    dish: MenuItem,
    onPriceClick: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        //Icon
        Icon(
            imageVector = Icons.Default.Fastfood,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(8.dp)
        )

        //Name and Ingredients
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = dish.key)
            Text(text = dish.ingredients)
            Button(
                onClick = { onPriceClick() }
            ) {
                Text(text = dish.cost.toString() + " " + stringResource(id = R.string.rub))
            }
        }
    }
}






















