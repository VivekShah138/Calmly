package com.example.calmly.player


import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SoundPlayerManager private constructor(private val context: Context) {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build().apply {
        repeatMode = Player.REPEAT_MODE_ONE
    }

    private val _currentPlayingSoundId = MutableStateFlow<Int?>(null)
    val currentPlayingSoundId: StateFlow<Int?> = _currentPlayingSoundId

    fun play(soundResId: Int, soundId: Int) {
        if (_currentPlayingSoundId.value == soundId) {
            // Already playing this sound, do nothing or resume if paused
            if (!player.isPlaying) player.play()
            return
        }

        player.setMediaItem(MediaItem.fromUri("android.resource://${context.packageName}/$soundResId"))
        player.prepare()
        player.play()
        _currentPlayingSoundId.value = soundId
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
        _currentPlayingSoundId.value = null
    }

    fun isPlaying(soundId: Int): Boolean {
        return player.isPlaying && _currentPlayingSoundId.value == soundId
    }

    fun release() {
        player.release()
    }

    companion object {
        @Volatile private var INSTANCE: SoundPlayerManager? = null
        fun getInstance(context: Context): SoundPlayerManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SoundPlayerManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}
