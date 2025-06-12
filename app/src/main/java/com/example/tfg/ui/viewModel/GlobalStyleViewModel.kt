package com.example.tfg.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.utils.GlobalStyleDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GlobalStyleViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val _lightColor = MutableStateFlow("#6750A4")
    val lightColor: StateFlow<String> = _lightColor

    private val _darkColor = MutableStateFlow("#2196F3")
    val darkColor: StateFlow<String> = _darkColor

    init {
        viewModelScope.launch {
            GlobalStyleDataStore.observeLightPrimary(context).collect { _lightColor.value = it }
        }
        viewModelScope.launch {
            GlobalStyleDataStore.observeDarkPrimary(context).collect { _darkColor.value = it }
        }
    }

    fun setLightPrimaryColor(hex: String) {
        viewModelScope.launch {
            GlobalStyleDataStore.savePrimaryColor(hex, _darkColor.value, context)
            _lightColor.value = hex
        }
    }

    fun setDarkPrimaryColor(hex: String) {
        viewModelScope.launch {
            GlobalStyleDataStore.savePrimaryColor(_lightColor.value, hex, context)
            _darkColor.value = hex
        }
    }
}
