package com.example.calmly.presentation.features.view_songs.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.mapper.SoundMapper
import com.example.calmly.navigation.CalmNavHost
import com.example.calmly.presentation.core_components.BottomNavigationBar
import com.example.calmly.presentation.features.view_songs.viewmodel.SharedPlayerViewModel

@Composable
fun MainScaffold(
    navController: NavHostController,
    viewModel: SharedPlayerViewModel ,
) {

    val context = LocalContext.current

    // Map raw SoundData to domain Sound using context only once
    val allSounds = remember {
        (SoundDataSource.meditationSounds + SoundDataSource.sleepSounds).map {
            SoundMapper.responseToDomain(context = context, soundData = it)
        }
    }

    // Tell ViewModel about mapped sounds once when 'allSounds' changes (only once here)
    LaunchedEffect(allSounds) {
        viewModel.setAllSounds(allSounds)
    }

    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
//    val soundName by viewModel.currentSoundName.collectAsState()
    val currentSound by viewModel.currentSound.collectAsState()

    Scaffold(
        bottomBar = {
            Column {
                if (currentPlayingSoundId != null) {
                    NowPlayingBar(
                        soundName = currentSound?.title ?: "Now Playing",
                        isPlaying = isPlaying,
                        onPlayPauseClick = viewModel::togglePlayback,
                        soundThumbnailResId = currentSound?.thumbnail ?: 0
                    )
                }
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        CalmNavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues).consumeWindowInsets(paddingValues = paddingValues),
            viewModel = viewModel
        )
    }
}