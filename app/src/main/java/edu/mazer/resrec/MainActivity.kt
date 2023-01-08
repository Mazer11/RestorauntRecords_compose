package edu.mazer.resrec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.compose.ResRecTheme
import dagger.hilt.android.AndroidEntryPoint
import edu.mazer.resrec.model.datastore.DataStoreRepo
import edu.mazer.resrec.navigation.NavGraph
import edu.mazer.resrec.utils.ChangeLocale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var application: ResRecApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val datastore = DataStoreRepo(context)

            val themeValue =
                datastore.readThemeFromDataStore.collectAsState(initial = isSystemInDarkTheme())
            application.getAppThemeFromDataStore(themeValue.value)
            val appLocale = datastore.readLocaleFromDataStore.collectAsState(initial = "en")

            val navController = rememberNavController()

            ResRecTheme(
                useDarkTheme = themeValue.value
            ) {
                ChangeLocale(lang = appLocale.value)

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController = navController, app = application)
                }
            }
        }
    }
}