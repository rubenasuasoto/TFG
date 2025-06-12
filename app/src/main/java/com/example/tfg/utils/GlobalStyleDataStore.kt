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

    private val PRIMARY_COLOR_KEY = stringPreferencesKey("primary_color")

    suspend fun savePrimaryColor(context: Context, colorHex: String) {
        context.dataStore.edit { it[PRIMARY_COLOR_KEY] = colorHex }
    }

    fun observePrimaryColor(context: Context): Flow<String> =
        context.dataStore.data.map { it[PRIMARY_COLOR_KEY] ?: "#6750A4" } // default Material 3
}
