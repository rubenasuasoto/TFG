package com.example.tfg.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.utils.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _fontScale = MutableStateFlow(1.0f)
    val fontScale: StateFlow<Float> = _fontScale

    init {
        viewModelScope.launch {
            SettingsDataStore.observeTheme(context).collect { _isDarkTheme.value = it }
        }
        viewModelScope.launch {
            SettingsDataStore.observeFontScale(context).collect { _fontScale.value = it }
        }
    }

    fun toggleDarkTheme() {
        viewModelScope.launch {
            val newValue = !_isDarkTheme.value
            SettingsDataStore.saveTheme(context, newValue)
            _isDarkTheme.value = newValue
        }
    }

    fun setFontScale(scale: Float) {
        viewModelScope.launch {
            SettingsDataStore.saveFontScale(context, scale)
            _fontScale.value = scale
        }
    }
}
