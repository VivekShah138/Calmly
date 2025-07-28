package com.example.calmly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.domain.local.model.Sound
import com.example.calmly.player.SoundPlayerManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SleepViewModel (
    val playerManager: SoundPlayerManager
) : ViewModel() {

    val sounds: List<Sound> = SoundDataSource.sleepSounds

    // Expose currently playing sound id as StateFlow
    val currentPlayingSoundId: StateFlow<Int?> = playerManager.currentPlayingSoundId
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val isPlaying: StateFlow<Boolean> = playerManager.isPlaying
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun onPlayPauseClicked(sound: Sound) {
        if (playerManager.currentPlayingSoundId.value == sound.id) {
            if (playerManager.isPlaying.value) {
                playerManager.pause()
            } else {
                playerManager.play(sound.resId, sound.id)
            }
        } else {
            playerManager.play(sound.resId, sound.id)
        }
    }
}
