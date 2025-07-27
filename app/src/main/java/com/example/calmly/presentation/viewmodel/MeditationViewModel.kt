package com.example.calmly.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.domain.local.model.Sound
import com.example.calmly.player.SoundPlayerManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MeditationViewModel(
    private val playerManager: SoundPlayerManager
) : ViewModel() {

    val sounds: List<Sound> = SoundDataSource.meditationSounds

    // Expose currently playing sound id as StateFlow
    val currentPlayingSoundId: StateFlow<Int?> = playerManager.currentPlayingSoundId
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onPlayPauseClicked(sound: Sound) {
        if (playerManager.isPlaying(sound.id)) {
            playerManager.pause()
        } else {
            playerManager.play(sound.resId, sound.id)
        }
    }
}
