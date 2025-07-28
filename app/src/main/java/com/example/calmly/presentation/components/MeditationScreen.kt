package com.example.calmly.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.navigation.BottomNavigationBar
import com.example.calmly.presentation.viewmodel.MeditationViewModel
import com.example.calmly.presentation.viewmodel.SharedPlayerViewModel
import org.koin.compose.viewmodel.koinViewModel

//@Composable
//fun MeditationScreen(
//    navController: NavController,
//    viewModel: MeditationViewModel = koinViewModel()
//) {
//
//    val playerManager = viewModel.playerManager
//
//    // Register BroadcastReceiver only once
//    DisposableEffect (Unit) {
//        playerManager.registerReceiver()
//        onDispose { playerManager.unregisterReceiver() }
//    }
//
//
//    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()
//    val isPlaying by viewModel.isPlaying.collectAsState()
//
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
//                navController = navController
//            )
//        }
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            items(viewModel.sounds) { sound ->
//                SoundCard(
//                    sound = sound,
//                    isPlaying = (currentPlayingSoundId == sound.id && isPlaying),
//                    onPlayPauseClick = { viewModel.onPlayPauseClicked(sound) }
//                )
//            }
//        }
//    }
//}

@Composable
fun MeditationScreen(
    navController: NavController,
    viewModel: SharedPlayerViewModel = koinViewModel()
) {
//    val playerManager = viewModel.playerManager

//    DisposableEffect(Unit) {
//        playerManager.registerReceiver()
//        onDispose { playerManager.unregisterReceiver() }
//    }

    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    val sounds = SoundDataSource.meditationSounds

    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(sounds) { sound ->
                SoundCard(
                    sound = sound,
                    isPlaying = (currentPlayingSoundId == sound.id && isPlaying),
                    onPlayPauseClick = { viewModel.onPlayPauseClicked(sound) }
                )
            }
        }
    }
}
