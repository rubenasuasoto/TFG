package com.example.tfg.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object GlobalStyleDataStore {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("global_style")


    private val LIGHT_PRIMARY_KEY = stringPreferencesKey("light_primary")
    private val DARK_PRIMARY_KEY = stringPreferencesKey("dark_primary")


    suspend fun savePrimaryColor(light: String, dark: String, context: Context) {
        context.dataStore.edit {
            it[LIGHT_PRIMARY_KEY] = light
            it[DARK_PRIMARY_KEY] = dark
        }
    }

    fun observeLightPrimary(context: Context): Flow<String> =
        context.dataStore.data.map { it[LIGHT_PRIMARY_KEY] ?: "#6750A4" }

    fun observeDarkPrimary(context: Context): Flow<String> =
        context.dataStore.data.map { it[DARK_PRIMARY_KEY] ?: "#2196F3" }
}