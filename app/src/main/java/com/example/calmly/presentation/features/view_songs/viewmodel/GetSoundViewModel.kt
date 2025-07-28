package com.example.calmly.presentation.features.view_songs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmly.domain.local.model.Sound
import com.example.calmly.domain.local.usecases.GetSoundUseCaseWrapper
import com.example.calmly.player.SoundPlayerManager
import com.example.calmly.presentation.features.view_songs.events.GetSoundEvents
import com.example.calmly.presentation.features.view_songs.states.GetSoundStates
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GetSoundViewModel(
    val playerManager: SoundPlayerManager,
    private val getSoundUseCaseWrapper: GetSoundUseCaseWrapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(GetSoundStates())
    val uiState: StateFlow<GetSoundStates> = _uiState.asStateFlow()

    init {
        observePlayerState()
        setAllSounds()
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            combine(
                playerManager.isPlaying, playerManager.currentPlayingSoundId
            ) { isPlaying, id ->
                val currentSound = _uiState.value.allSounds.find { it.id == id }
                _uiState.update {
                    it.copy(
                        isPlaying = isPlaying,
                        currentPlayingSoundId = id,
                        currentSound = currentSound
                    )
                }
            }.collect()
        }
    }

    private fun setAllSounds() {
        viewModelScope.launch {
            val allSounds = getSoundUseCaseWrapper.getAllSoundsUseCase()
            val sleepSounds = getSoundUseCaseWrapper.getSleepSoundUseCase()
            val meditationSounds = getSoundUseCaseWrapper.getMeditationSoundUseCase()
            _uiState.update {
                it.copy(
                    allSounds = allSounds,
                    meditationSounds = meditationSounds,
                    sleepSounds = sleepSounds
                )
            }
        }

    }

    fun onEvent(event: GetSoundEvents) {
        when (event) {
            is GetSoundEvents.PlayPauseClicked -> {
                val currentId = playerManager.currentPlayingSoundId.value
                val isSame = currentId == event.sound.id

                if (isSame) {
                    if (playerManager.isPlaying.value) {
                        playerManager.pause()
                    } else {
                        playerManager.play(event.sound.resId, event.sound.id)
                    }
                } else {
                    playerManager.play(event.sound.resId, event.sound.id)
                }

                _uiState.update { it.copy(lastPlayedSound = event.sound) }
            }

            is GetSoundEvents.TogglePlayback -> {
                val isPlaying = playerManager.isPlaying.value
                val currentId = playerManager.currentPlayingSoundId.value
                val allSounds = _uiState.value.allSounds
                val lastSound = _uiState.value.lastPlayedSound

                if (isPlaying) {
                    playerManager.pause()
                } else {
                    val toPlay = lastSound ?: allSounds.find { it.id == currentId }
                    toPlay?.let {
                        playerManager.play(it.resId, it.id)
                        _uiState.update { state -> state.copy(lastPlayedSound = it) }
                    }
                }
            }
        }
    }
}
