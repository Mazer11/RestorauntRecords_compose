package edu.mazer.resrec

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ResRecApp : Application() {
    var isDarkTheme = false

    fun getAppThemeFromDataStore(
        themeValue: Boolean
    ) {
        isDarkTheme = themeValue
    }

    fun switchAppTheme() {
        isDarkTheme = !isDarkTheme
    }
}