package com.example.tfg.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")


object SettingsDataStore {

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    private val FONT_SCALE_KEY = floatPreferencesKey("font_scale")
    private val IDIOMA_KEY = stringPreferencesKey("idioma")

    suspend fun saveTheme(context: Context, isDark: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = isDark
        }
    }

    suspend fun saveFontScale(context: Context, scale: Float) {
        context.dataStore.edit { prefs ->
            prefs[FONT_SCALE_KEY] = scale
        }
    }

    fun observeTheme(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[DARK_THEME_KEY] == true }

    fun observeFontScale(context: Context): Flow<Float> =
        context.dataStore.data.map { it[FONT_SCALE_KEY] ?: 1.0f }



    suspend fun saveIdioma(context: Context, idioma: Idioma) {
        context.dataStore.edit { prefs ->
            prefs[IDIOMA_KEY] = idioma.name
        }
    }

    fun observeIdioma(context: Context): Flow<Idioma> =
        context.dataStore.data.map { prefs ->
            Idioma.valueOf(prefs[IDIOMA_KEY] ?: Idioma.ESP.name)
        }

}
