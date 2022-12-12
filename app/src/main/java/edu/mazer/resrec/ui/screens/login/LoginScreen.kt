package edu.mazer.resrec.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.mazer.resrec.navigation.NavigationRoutes

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    navController: NavController,
) {
    val userLogin = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val isUserLoginValid by remember {
        derivedStateOf {
            userLogin.value.isNotEmpty() &&
                    userLogin.value.length <= 20
        }
    }

    val isPasswordValid by remember {
        derivedStateOf {
            password.value.isNotEmpty() &&
                    password.value.length <= 20
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(Alignment.Center),
        ) {
            TextField(
                value = userLogin.value,
                onValueChange = {
                    userLogin.value = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Man, contentDescription = "Login")
                },
                label = {
                    Text("Enter login...")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                ),
                isError = isUserLoginValid.not()
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            TextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Password, contentDescription = "Password")
                },
                label = {
                    Text("Enter login...")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                isError = isPasswordValid.not()
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Button(
                onClick = {
                    if (isUserLoginValid && isPasswordValid)
                        navController.navigate(NavigationRoutes.homeScreen.route)
                },
            ) {
                Text(text = "Sign In")
            }
        }
    }
}






























