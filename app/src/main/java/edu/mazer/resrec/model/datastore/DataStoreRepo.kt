package edu.mazer.resrec.model.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Singleton

private const val DATASTORE_NAME = "resrec_datastore"
private const val THEME_PREFERENCE = "theme"
private const val LOCALISATION_PREFERENCE = "locale"

@Singleton
class DataStoreRepo(
    private val context: Context
) {

    companion object {
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    private object PreferenceKey {
        val theme_key = booleanPreferencesKey(THEME_PREFERENCE)
        val locale_key = stringPreferencesKey(LOCALISATION_PREFERENCE)
    }

    val readThemeFromDataStore: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val theme = preferences[PreferenceKey.theme_key] ?: true
            theme
        }

    val readLocaleFromDataStore: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val locale = preferences[PreferenceKey.locale_key] ?: "en"
            locale
        }

    suspend fun editThemePreference() {
        context.dataStore.edit { pref ->
            if (pref[PreferenceKey.theme_key] == null)
                pref[PreferenceKey.theme_key] = false
            else
                pref[PreferenceKey.theme_key] = !pref[PreferenceKey.theme_key]!!
        }
    }

    suspend fun editLocalePreference(
        locale: String
    ) {
        context.dataStore.edit { pref ->
            if (pref[PreferenceKey.locale_key] == null)
                pref[PreferenceKey.locale_key] = "ru"
            else
                pref[PreferenceKey.locale_key] = locale
        }
    }
}