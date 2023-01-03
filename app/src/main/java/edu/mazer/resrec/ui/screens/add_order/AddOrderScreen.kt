package edu.mazer.resrec.ui.screens.add_order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderScreen(
    navController: NavController
) {
    Scaffold() {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Add order screen")
        }
    }
}