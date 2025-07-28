package com.example.calmly.presentation.features.view_songs.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.mapper.SoundMapper
import com.example.calmly.presentation.core_components.AppTopBar
import com.example.calmly.presentation.features.view_songs.events.GetSoundEvents
import com.example.calmly.presentation.features.view_songs.viewmodel.GetSoundViewModel

@Composable
fun MeditationScreen(
    navController: NavController,
    viewModel: GetSoundViewModel
) {
    val states by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Meditation"
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(states.meditationSounds) { sound ->
                SoundCard(
                    sound = sound,
                    isPlaying = (states.currentPlayingSoundId == sound.id && states.isPlaying),
                    onPlayPauseClick = {
                        viewModel.onEvent(
                            GetSoundEvents.PlayPauseClicked(sound)
                        )
                    }
                )
            }
        }
    }
}
