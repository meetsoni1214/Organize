package com.example.organize.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlinx.coroutines.flow.Flow


private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)
class SettingsDataStore(context: Context) {
    private val IS_SOCIALS = booleanPreferencesKey("is_socials")

    suspend fun saveLayoutToPreferenceStore(isSocials: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[IS_SOCIALS] = isSocials
        }
    }
    val prefereceFlow: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            }else {
                throw it
            }
        }
        .map { preferences ->
            // On the first run of the app, we will display all socials by default
            preferences[IS_SOCIALS] ?: true
        }
}