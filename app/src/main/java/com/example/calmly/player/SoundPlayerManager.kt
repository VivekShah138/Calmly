package com.example.calmly.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.example.calmly.service.MediaPlayerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SoundPlayerManager private constructor(private val context: Context) {

    private val _currentPlayingSoundId = MutableStateFlow<Int?>(null)
    val currentPlayingSoundId: StateFlow<Int?> = _currentPlayingSoundId

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val playbackStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == MediaPlayerService.ACTION_PLAYBACK_STATE_CHANGED) {
                val playing = intent.getBooleanExtra(MediaPlayerService.EXTRA_IS_PLAYING, false)
                val currentUri = intent.getStringExtra(MediaPlayerService.EXTRA_CURRENT_URI)

                _isPlaying.value = playing
                Log.d("SoundPlayerManager", "Playback state changed: isPlaying=$playing, uri=$currentUri")
            }
        }
    }

    fun registerReceiver() {
        val filter = IntentFilter(MediaPlayerService.ACTION_PLAYBACK_STATE_CHANGED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(playbackStateReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            context.registerReceiver(playbackStateReceiver, filter)
        }
    }

    fun unregisterReceiver() {
        context.unregisterReceiver(playbackStateReceiver)
    }


    fun play(
        soundResId: Int,
        soundId: Int,
        title: String,
        artist: String,
        artworkResId: Int
    ) {
        if (_currentPlayingSoundId.value == soundId) {

            sendCommand(MediaPlayerService.ACTION_PLAY, soundResId, title, artist, artworkResId)
            return
        }

        _currentPlayingSoundId.value = soundId
        sendCommand(MediaPlayerService.ACTION_PLAY, soundResId, title, artist, artworkResId)
    }

    fun pause() {
        sendCommand(MediaPlayerService.ACTION_PAUSE)
    }

    private fun sendCommand(
        action: String,
        soundResId: Int? = null,
        title: String? = null,
        artist: String? = null,
        artworkResId: Int? = null
    ) {
        val intent = Intent(context, MediaPlayerService::class.java).apply {
            this.action = action
            soundResId?.let {
                putExtra(
                    MediaPlayerService.EXTRA_URI,
                    "android.resource://${context.packageName}/$it"
                )
            }
            title?.let { putExtra(MediaPlayerService.EXTRA_TITLE, it) }
            artist?.let { putExtra(MediaPlayerService.EXTRA_ARTIST, it) }
            artworkResId?.let { putExtra(MediaPlayerService.EXTRA_ARTWORK, it) }
        }
        context.startService(intent)
    }

    companion object {
        @Volatile
        private var INSTANCE: SoundPlayerManager? = null

        fun getInstance(context: Context): SoundPlayerManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SoundPlayerManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}

