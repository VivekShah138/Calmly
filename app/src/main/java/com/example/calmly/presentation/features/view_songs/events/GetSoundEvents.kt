package com.example.calmly.presentation.features.view_songs.events

import com.example.calmly.domain.local.model.Sound

sealed class GetSoundEvents {
    data class PlayPauseClicked(val sound: Sound) : GetSoundEvents()
    data object TogglePlayback : GetSoundEvents()
}
