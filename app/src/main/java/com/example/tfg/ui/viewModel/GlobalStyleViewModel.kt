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
    private val _primaryColorHex = MutableStateFlow("#6750A4")
    val primaryColorHex: StateFlow<String> = _primaryColorHex

    init {
        viewModelScope.launch {
            GlobalStyleDataStore.observePrimaryColor(context).collect {
                _primaryColorHex.value = it
            }
        }
    }

    fun setPrimaryColor(hex: String) {
        viewModelScope.launch {
            GlobalStyleDataStore.savePrimaryColor(context, hex)
            _primaryColorHex.value = hex
        }
    }
}
