package com.example.tfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tfg.ui.viewModel.AppSettingsViewModel
import com.example.tfg.ui.viewModel.GlobalStyleViewModel


class MainActivity : ComponentActivity() {
    private lateinit var settingsViewModel: AppSettingsViewModel
    private lateinit var styleViewModel: GlobalStyleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsViewModel = AppSettingsViewModel(application)
        styleViewModel = GlobalStyleViewModel(application)

        setContent {
            MyApp(
                settingsViewModel = settingsViewModel,
                styleViewModel = styleViewModel
            )
        }
    }
}

