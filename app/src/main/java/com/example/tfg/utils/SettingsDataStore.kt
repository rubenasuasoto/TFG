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


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")


object SettingsDataStore {

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    private val FONT_SCALE_KEY = floatPreferencesKey("font_scale")

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
}
