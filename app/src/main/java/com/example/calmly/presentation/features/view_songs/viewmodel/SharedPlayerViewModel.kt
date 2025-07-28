package com.example.calmly.presentation.features.view_songs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmly.domain.local.model.Sound
import com.example.calmly.player.SoundPlayerManager
import kotlinx.coroutines.flow.*

class SharedPlayerViewModel(
    val playerManager: SoundPlayerManager
) : ViewModel() {

    val currentPlayingSoundId: StateFlow<Int?> =
        playerManager.currentPlayingSoundId
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val isPlaying: StateFlow<Boolean> =
        playerManager.isPlaying
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)


    private var lastPlayedSound: Sound? = null

    private var allSounds: List<Sound> = emptyList()

    val currentSound: StateFlow<Sound?> = currentPlayingSoundId
        .map { id -> allSounds.find { it.id == id } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)


    fun setAllSounds(sounds: List<Sound>) {
        allSounds = sounds
    }

    fun onPlayPauseClicked(sound: Sound) {
        val isSameSound = playerManager.currentPlayingSoundId.value == sound.id
        lastPlayedSound = sound

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

    fun togglePlayback() {
        val isPlaying = playerManager.isPlaying.value
        val currentSoundId = playerManager.currentPlayingSoundId.value

        if (isPlaying) {
            playerManager.pause()
        } else {
            val sound = lastPlayedSound ?: allSounds.find { it.id == currentSoundId }

            sound?.let {
                playerManager.play(it.resId, it.id)
                lastPlayedSound = it
            }
        }
    }
}
