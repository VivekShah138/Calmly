package com.example.calmly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calmly.navigation.Screens
import com.example.calmly.presentation.components.MeditationScreen
import com.example.calmly.presentation.components.SleepScreen
import com.example.calmly.presentation.viewmodel.SharedPlayerViewModel
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
                NavHost(
                    navController = navController,
                    startDestination = Screens.MeditationScreen
                ){
                    composable<Screens.MeditationScreen>{
                        MeditationScreen(
                            navController = navController
                        )
                    }
                    composable<Screens.SleepScreen> {
                        SleepScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPlayerViewModel.playerManager.unregisterReceiver()
    }
}
