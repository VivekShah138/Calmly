package com.example.calmly.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.calmly.navigation.BottomNavigationBar
import com.example.calmly.presentation.viewmodel.SleepViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SleepScreen(
    navController: NavController,
    viewModel: SleepViewModel = koinViewModel()
) {
    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(viewModel.sounds) { sound ->
                SoundCard(
                    sound = sound,
                    isPlaying = currentPlayingSoundId == sound.id,
                    onPlayPauseClick = { viewModel.onPlayPauseClicked(sound) }
                )
            }
        }
    }
}