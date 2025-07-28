package com.example.calmly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calmly.navigation.CalmNavHost
import com.example.calmly.navigation.Screens
import com.example.calmly.presentation.features.view_songs.components.MainScaffold
import com.example.calmly.presentation.features.view_songs.components.MeditationScreen
import com.example.calmly.presentation.features.view_songs.components.SleepScreen
import com.example.calmly.presentation.features.view_songs.viewmodel.SharedPlayerViewModel
import com.example.calmly.ui.theme.CalmlyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val sharedPlayerViewModel: SharedPlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPlayerViewModel.playerManager.registerReceiver()

        setContent {
            CalmlyTheme {
                val navController = rememberNavController()

                MainScaffold(
                    navController = navController,
                    viewModel = sharedPlayerViewModel
                )

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPlayerViewModel.playerManager.unregisterReceiver()
    }
}
