package edu.mazer.resrec

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ResRecApp : Application() {
    val isDarkTheme = mutableStateOf(false)

    fun getAppThemeFromDataStore(
        themeValue: Boolean
    ) {
        isDarkTheme.value = themeValue
    }

    fun switchAppTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}