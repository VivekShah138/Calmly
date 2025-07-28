package com.example.calmly.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmly.domain.local.model.Sound
import com.example.calmly.player.SoundPlayerManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SharedPlayerViewModel(
    val playerManager: SoundPlayerManager
) : ViewModel() {

    val currentPlayingSoundId: StateFlow<Int?> =
        playerManager.currentPlayingSoundId.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val isPlaying: StateFlow<Boolean> =
        playerManager.isPlaying.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun onPlayPauseClicked(sound: Sound) {
        val isSameSound = playerManager.currentPlayingSoundId.value == sound.id
        if (isSameSound) {
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
