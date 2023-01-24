package edu.mazer.resrec.ui.screens.login

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.mazer.resrec.R
import edu.mazer.resrec.model.AuthResult
import edu.mazer.resrec.navigation.NavigationRoutes
import edu.mazer.resrec.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    navController: NavController,
    loginVm: LoginViewModel
) {
    val userLogin = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val snackBatHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val authResult = loginVm.authResult.observeAsState()

    val showClearToLogin = remember{ derivedStateOf { userLogin.value.isNotEmpty() }}
    val showClearToPassword = remember{ derivedStateOf { password.value.isNotEmpty() }}

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

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBatHostState,
            ) { data ->
                Snackbar(
                    shape = RoundedCornerShape(8.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(16.dp)
                        .width(200.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = data.visuals.message,
                        )
                    }
                }
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
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
                    trailingIcon = {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showClearToLogin.value, enter = fadeIn(), exit = fadeOut()
                        ) {
                            IconButton(onClick = { userLogin.value = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    label = {
                        Text(stringResource(R.string.username))
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
                    trailingIcon = {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = showClearToPassword.value, enter = fadeIn(), exit = fadeOut()
                        ) {
                            IconButton(onClick = { password.value = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    label = {
                        Text(stringResource(R.string.password))
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

                val errorMessage = stringResource(R.string.credential_error)

                Button(
                    onClick = {
                        if (isUserLoginValid && isPasswordValid) {
                            loginVm.signIn(userLogin.value, password.value) {
                                navController.popBackStack()
                                navController.navigate(route = NavigationRoutes.HOME.route)
                            }
                        } else
                            scope.launch {
                                snackBatHostState.showSnackbar(errorMessage)
                            }
                        if (authResult.value is AuthResult.Failure) {
                            scope.launch {
                                snackBatHostState.showSnackbar(errorMessage)
                            }
                        }
                    },
                ) {
                    Text(text = stringResource(R.string.signin))
                }
            }
        }
    }
}