package com.example.calmly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.navigation.compose.rememberNavController
import com.example.calmly.presentation.features.view_songs.components.MainScaffold
import com.example.calmly.presentation.features.view_songs.viewmodel.GetSoundViewModel
import com.example.calmly.ui.theme.CalmlyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val getSoundViewModel: GetSoundViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        getSoundViewModel.playerManager.registerReceiver()

        setContent {
            CalmlyTheme {
                val navController = rememberNavController()

                MainScaffold(
                    navController = navController,
                    viewModel = getSoundViewModel
                )

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getSoundViewModel.playerManager.unregisterReceiver()
    }
}
