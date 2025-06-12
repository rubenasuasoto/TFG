package com.example.tfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tfg.ui.navigation.AppNavigation
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.viewModel.AppSettingsViewModel


class MainActivity : ComponentActivity() {
    private lateinit var settingsViewModel: AppSettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = AppSettingsViewModel(application)

        setContent {
            MyApp(settingsViewModel)
        }
    }
}

