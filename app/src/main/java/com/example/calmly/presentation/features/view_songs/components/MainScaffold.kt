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
import com.example.calmly.presentation.features.view_songs.events.GetSoundEvents
import com.example.calmly.presentation.features.view_songs.viewmodel.GetSoundViewModel

@Composable
fun MainScaffold(
    navController: NavHostController,
    viewModel: GetSoundViewModel,
) {

    val states by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            Column {
                if (states.currentPlayingSoundId != null) {
                    NowPlayingBar(
                        soundName = states.currentSound?.title ?: "Now Playing",
                        isPlaying = states.isPlaying,
                        onPlayPauseClick = {
                            viewModel.onEvent(GetSoundEvents.TogglePlayback)
                        },
                        soundThumbnailResId = states.currentSound?.thumbnail ?: 0,
                        soundAuthor = states.currentSound?.author ?: "No Author"
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