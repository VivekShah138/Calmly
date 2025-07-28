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
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.example.calmly.R

class MediaPlayerService : Service() {

    companion object {
        const val CHANNEL_ID = "calmly_media_channel"
        const val NOTIFICATION_ID = 1

        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_TOGGLE_PLAYBACK = "ACTION_TOGGLE_PLAYBACK"

        const val EXTRA_URI = "EXTRA_URI"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_ARTIST = "extra_artist"
        const val EXTRA_ARTWORK = "extra_artwork"

        const val ACTION_PLAYBACK_STATE_CHANGED = "com.example.calmly.PLAYBACK_STATE_CHANGED"
        const val EXTRA_IS_PLAYING = "extra_is_playing"
        const val EXTRA_CURRENT_URI = "extra_current_uri"
    }

    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private lateinit var notificationManager: NotificationManager

    private var currentTitle: String? = null
    private var currentArtist: String? = null
    private var currentArtworkResId: Int? = null

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
                val title = intent.getStringExtra(EXTRA_TITLE) ?: "Unknown Title"
                val artist = intent.getStringExtra(EXTRA_ARTIST) ?: "Unknown Artist"
                val artworkResId = intent.getIntExtra(EXTRA_ARTWORK, R.drawable.rain)

                uriString?.let {
                    playSound(Uri.parse(it), title, artist, artworkResId)
                }
            }
            ACTION_PAUSE -> pauseSound()
            ACTION_STOP -> stopSound()
            ACTION_TOGGLE_PLAYBACK -> togglePlayback()
        }
        return START_STICKY
    }

    private fun playSound(uri: Uri, title: String, artist: String, artworkResId: Int) {
        Log.d("MediaPlayerService", "playSound: $uri")

        val currentUri = player.currentMediaItem?.localConfiguration?.uri

        if (currentUri == uri) {
            if (!player.isPlaying) player.play()
        } else {
            currentTitle = title
            currentArtist = artist
            currentArtworkResId = artworkResId

            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(title)
                        .setArtist(artist)
                        .setArtworkUri(Uri.parse("android.resource://$packageName/$artworkResId"))
                        .build()
                )
                .build()

            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }

    private fun pauseSound() {
        if (player.isPlaying) player.pause()
    }

    private fun togglePlayback() {
        if (player.isPlaying) player.pause() else player.play()
    }

    private fun stopSound() {
        player.stop()
        stopForeground(true)
        stopSelf()
    }


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
            .setContentTitle(currentTitle ?: "Calmly")
            .setContentText(currentArtist ?: "Playing sound")
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
