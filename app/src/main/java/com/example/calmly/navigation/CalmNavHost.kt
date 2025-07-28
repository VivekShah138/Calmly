package com.example.calmly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calmly.presentation.features.view_songs.components.MeditationScreen
import com.example.calmly.presentation.features.view_songs.components.SleepScreen
import com.example.calmly.presentation.features.view_songs.viewmodel.GetSoundViewModel

@Composable
fun CalmNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: GetSoundViewModel,
    startDestination: Screens = Screens.MeditationScreen
){
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<Screens.MeditationScreen>{
            MeditationScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable<Screens.SleepScreen> {
            SleepScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}