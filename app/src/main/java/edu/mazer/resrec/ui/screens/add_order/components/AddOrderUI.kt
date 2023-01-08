package edu.mazer.resrec.ui.screens.add_order.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import edu.mazer.resrec.R
import edu.mazer.resrec.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrderUI(
    searchText: String,
    placeholderText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    matchesFound: Boolean,
    floatingAB: @Composable () -> Unit,
    results: @Composable () -> Unit
) {
    var checkText by remember { mutableStateOf(searchText) }
    val showClearButton by remember { derivedStateOf { checkText.isNotEmpty() } }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchText,
                        onValueChange = {
                            onSearchTextChanged(it)
                            checkText = it
                        },
                        placeholder = { Text(text = placeholderText) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "")
                        },
                        textStyle = AppTypography.bodyMedium,
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = showClearButton, enter = fadeIn(), exit = fadeOut()
                            ) {
                                IconButton(onClick = { onClearClick() }) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = ""
                                    )
                                }
                            }
                        },
                        maxLines = 1,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                modifier = Modifier.padding(top = 4.dp)
            )
        },
        floatingActionButton = {
            floatingAB()
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (matchesFound) {
                results()
            } else if (searchText.isNotEmpty())
                NoSearchResults()
        }
    }
}


@Composable
fun NoSearchResults() {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = stringResource(R.string.matches_not_found))
    }
}