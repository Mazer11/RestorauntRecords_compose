package edu.mazer.resrec.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.mazer.resrec.R
import edu.mazer.resrec.ResRecApp
import edu.mazer.resrec.model.datastore.DataStoreRepo
import edu.mazer.resrec.utils.ChangeLocale
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    navController: NavController,
    app: ResRecApp
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val datastore = DataStoreRepo(context)

    var locale = datastore.readLocaleFromDataStore.collectAsState(initial = "").value
    var localeName by remember { mutableStateOf("") }
    var expandDropDownMenu by remember { mutableStateOf(false) }

    val isDarkTheme = app.isDarkTheme

    localeName = when (locale) {
        "en" -> {
            ChangeLocale(lang = "en")
            "English"
        }
        "ru" -> {
            ChangeLocale(lang = "ru")
            "Русский"
        }
        else -> "Русский"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SettingsTopAppBar(
                locale = locale,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.darkmode_enable),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Switch(
                    checked = isDarkTheme.value,
                    onCheckedChange = {
                        app.switchAppTheme()
                        scope.launch {
                            datastore.editThemePreference()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.language),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = localeName,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp)
                        .clickable { expandDropDownMenu = true }
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = expandDropDownMenu,
                    onDismissRequest = { expandDropDownMenu = false },
                    offset = DpOffset(x = 8.dp, y = (-8).dp),
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "English") },
                        onClick = {
                            scope.launch {
                                datastore.editLocalePreference("en")
                            }
                            locale = "en"
                            localeName = "English"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Русский") },
                        onClick = {
                            scope.launch {
                                datastore.editLocalePreference("ru")
                            }
                            locale = "ru"
                            localeName = "Русский"
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    locale: String,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back button")
            }
        },
        title = {
            Text(
                text = stringResource(R.string.settings),
                maxLines = if (locale == "en") 1
                else 1
            )
        }
    )
}