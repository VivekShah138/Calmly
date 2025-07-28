package com.example.calmly.presentation.features.view_songs.states

import com.example.calmly.domain.local.model.Sound

data class GetSoundStates(
    val isPlaying: Boolean = false,
    val currentPlayingSoundId: Int? = null,
    val currentSound: Sound? = null,
    val allSounds: List<Sound> = emptyList(),
    val meditationSounds: List<Sound> = emptyList(),
    val sleepSounds: List<Sound> = emptyList(),
    val lastPlayedSound: Sound? = null
)
