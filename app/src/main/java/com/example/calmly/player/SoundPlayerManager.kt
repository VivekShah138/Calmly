package com.example.calmly.player
//
//import android.content.Context
//import androidx.annotation.RawRes
//import androidx.media3.common.MediaItem
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.datasource.RawResourceDataSource
//
//class SoundPlayerManager {
//    private var exoPlayer: ExoPlayer? = null
//    private var currentResId: Int? = null
//
//    fun play(context: Context, @RawRes resId: Int) {
//        if (currentResId == resId) {
//            pause()
//            return
//        }
//        release()
//
//        exoPlayer = ExoPlayer.Builder(context).build().apply {
//            val mediaUri = RawResourceDataSource.buildRawResourceUri(resId)
//            val mediaItem = MediaItem.fromUri(mediaUri)
//
//            setMediaItem(mediaItem)
//            repeatMode = ExoPlayer.REPEAT_MODE_ONE
//            prepare()
//            play()
//        }
//        currentResId = resId
//    }
//
//    fun pause() {
//        exoPlayer?.pause()
//    }
//
//    fun release() {
//        exoPlayer?.release()
//        exoPlayer = null
//        currentResId = null
//    }
//}


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
