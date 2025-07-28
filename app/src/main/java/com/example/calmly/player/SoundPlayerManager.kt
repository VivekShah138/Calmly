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
                Log.d("SoundPlayerManager", "Playback state changed: isPlayingState=${_isPlaying.value}, uri=$currentUri")

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


    fun play(soundResId: Int, soundId: Int) {
        if (_currentPlayingSoundId.value == soundId) {
            // Already playing, resume if needed
            sendCommand(MediaPlayerService.ACTION_PLAY, soundResId)
            return
        }


        _currentPlayingSoundId.value = soundId
        sendCommand(MediaPlayerService.ACTION_PLAY, soundResId)
    }

    fun pause() {
        sendCommand(MediaPlayerService.ACTION_PAUSE)
    }

    fun stop() {
        _currentPlayingSoundId.value = null
        sendCommand(MediaPlayerService.ACTION_STOP)
    }

    fun isPlaying(soundId: Int): Boolean {
        // We assume it's playing if soundId matches and not null
        return _currentPlayingSoundId.value == soundId
    }


    private fun sendCommand(action: String, soundResId: Int? = null) {
        val intent = Intent(context, MediaPlayerService::class.java).apply {
            this.action = action
            soundResId?.let {
                putExtra(
                    MediaPlayerService.EXTRA_URI,
                    "android.resource://${context.packageName}/$it"
                )
            }
        }
        context.startService(intent)
    }

    companion object {
        @Volatile private var INSTANCE: SoundPlayerManager? = null
        fun getInstance(context: Context): SoundPlayerManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SoundPlayerManager(context.applicationContext).also { INSTANCE = it }
            }
    }
}
