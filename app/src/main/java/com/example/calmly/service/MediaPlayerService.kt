package com.example.calmly.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession

class MediaPlayerService : Service() {

    companion object {
        const val CHANNEL_ID = "calmly_media_channel"
        const val NOTIFICATION_ID = 1
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_TOGGLE_PLAYBACK = "ACTION_TOGGLE_PLAYBACK"
        const val EXTRA_URI = "EXTRA_URI"
        const val ACTION_PLAYBACK_STATE_CHANGED = "com.example.calmly.PLAYBACK_STATE_CHANGED"
        const val EXTRA_IS_PLAYING = "extra_is_playing"
        const val EXTRA_CURRENT_URI = "extra_current_uri"
    }

    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()

        player = ExoPlayer.Builder(this).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    updateNotification()
                    sendPlaybackStateBroadcast(isPlaying)
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    // Notify new media item change
                    sendPlaybackStateBroadcast(player.isPlaying)
                }
            })
        }

        mediaSession = MediaSession.Builder(this, player).build()

        startForeground(NOTIFICATION_ID, buildNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val uriString = intent.getStringExtra(EXTRA_URI)
                uriString?.let { playSound(Uri.parse(it)) }
            }
            ACTION_PAUSE -> pauseSound()
            ACTION_STOP -> stopSound()
            ACTION_TOGGLE_PLAYBACK -> togglePlayback()
        }
        return START_STICKY
    }

    private fun playSound(uri: Uri) {
        Log.d("MediaPlayerService", "playSound: $uri")

        val currentUri = player.currentMediaItem?.localConfiguration?.uri

        if (currentUri == uri) {
            // Same media, just play if not playing
            if (!player.isPlaying) player.play()
        } else {
            // Different media, set and play
            player.setMediaItem(MediaItem.fromUri(uri))
            player.prepare()
            player.play()
        }
    }

    private fun pauseSound() {
        if (player.isPlaying) {
            player.pause()
        }
    }

    private fun togglePlayback() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    private fun stopSound() {
        player.stop()
        stopForeground(true)
        stopSelf()
    }

    @OptIn(UnstableApi::class)
    private fun buildNotification(): Notification {
        val playPauseAction = if (player.isPlaying) {
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_pause,
                "Pause",
                getPendingIntent(ACTION_TOGGLE_PLAYBACK)
            ).build()
        } else {
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_play,
                "Play",
                getPendingIntent(ACTION_TOGGLE_PLAYBACK)
            ).build()
        }

        val stopAction = NotificationCompat.Action.Builder(
            android.R.drawable.ic_menu_close_clear_cancel,
            "Stop",
            getPendingIntent(ACTION_STOP)
        ).build()

        val smallIcon = if (player.isPlaying) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Calmly")
            .setContentText(if (player.isPlaying) "Playing sound" else "Paused")
            .setSmallIcon(smallIcon)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(playPauseAction)
            .addAction(stopAction)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionCompatToken)
                    .setShowActionsInCompactView(0, 1)
            )
            .build()
    }

    private fun updateNotification() {
        notificationManager.notify(NOTIFICATION_ID, buildNotification())
    }

    private fun getPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaPlayerService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(this, action.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Calmly Media Playback",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        player.release()
        mediaSession.release()
        super.onDestroy()
    }

    private fun sendPlaybackStateBroadcast(isPlaying: Boolean) {
        val currentUri = player.currentMediaItem?.localConfiguration?.uri
        val intent = Intent(ACTION_PLAYBACK_STATE_CHANGED).apply {
            putExtra(EXTRA_IS_PLAYING, isPlaying)
            putExtra(EXTRA_CURRENT_URI, currentUri.toString())
        }
        sendBroadcast(intent)
    }

}


