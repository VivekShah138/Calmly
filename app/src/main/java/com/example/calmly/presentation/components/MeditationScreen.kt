package com.example.calmly.presentation.components

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
import com.example.calmly.navigation.BottomNavigationBar
import com.example.calmly.presentation.viewmodel.SharedPlayerViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MeditationScreen(
    navController: NavController,
    viewModel: SharedPlayerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

//    val sounds = SoundDataSource.meditationSounds

    val sounds = SoundDataSource.meditationSounds.map {
        SoundMapper.responseToDomain(
            context = context,
            soundData = it
        )
    }

    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(sounds) { sound ->
                SoundCard2(
                    sound = sound,
                    isPlaying = (currentPlayingSoundId == sound.id && isPlaying),
                    onPlayPauseClick = { viewModel.onPlayPauseClicked(sound) }
                )
            }
        }
    }
}
