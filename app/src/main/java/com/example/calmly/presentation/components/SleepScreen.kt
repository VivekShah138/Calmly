package com.example.calmly.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.calmly.navigation.BottomNavigationBar
import com.example.calmly.presentation.viewmodel.MeditationViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SleepScreen(
    navController: NavController,
    viewModel: MeditationViewModel = koinViewModel()
) {
//    val currentPlayingSoundId by viewModel.currentPlayingSoundId.collectAsState()
//
//    LazyColumn {
//        items(viewModel.sounds) { sound ->
//            SoundCard(
//                sound = sound,
//                isPlaying = currentPlayingSoundId == sound.id,
//                onPlayPauseClick = { viewModel.onPlayPauseClicked(sound) }
//            )
//        }
//    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Sleep Screen"
            )
        }
    }

}