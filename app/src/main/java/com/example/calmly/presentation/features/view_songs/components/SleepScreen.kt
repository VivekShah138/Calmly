package com.example.calmly.presentation.features.view_songs.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.mapper.SoundMapper
import com.example.calmly.presentation.core_components.AppTopBar
import com.example.calmly.presentation.core_components.BottomNavigationBar
import com.example.calmly.presentation.features.view_songs.viewmodel.SharedPlayerViewModel
import com.example.calmly.ui.theme.DarkBackground
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SleepScreen(
    navController: NavController,
    viewModel: SharedPlayerViewModel
) {
    val context = LocalContext.current
    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    val sounds = SoundDataSource.sleepSounds.map {
        SoundMapper.responseToDomain(
            context = context, soundData = it
        )
    }


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Sleep",
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(sounds) { sound ->
                SoundCard(
                    sound = sound,
                    isPlaying = (currentPlayingSoundId == sound.id && isPlaying),
                    onPlayPauseClick = { viewModel.onPlayPauseClicked(sound) })
            }
        }
    }
}
