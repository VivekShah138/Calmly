package com.example.calmly.data.local.data_source

import com.example.calmly.domain.local.model.Sound
import com.example.calmly.R



object SoundDataSource {

    val meditationSounds = listOf(
        Sound(id = 1, title = "Forest", resId = R.raw.morning_forest_ambiance,R.drawable.ic_launcher_background),
        Sound(id = 2, title = "Rain", resId = R.raw.real_rain_sound,R.drawable.ic_launcher_background),
        Sound(id = 3, title = "Campfire", resId = R.raw.campfire,R.drawable.ic_launcher_background),
        Sound(id = 4, title = "Ocean", resId = R.raw.ocean_waves,R.drawable.ic_launcher_background),
        Sound(id = 5, title = "Wind", resId = R.raw.desert_wind,R.drawable.ic_launcher_background),
    )

    val sleepSounds = listOf(
        Sound(id = 101, title = "White Noise", resId = R.raw.white_noise, thumbnail = R.drawable.ic_launcher_background),
        Sound(id = 102, title = "Lullaby", resId = R.raw.short_lullaby_song, thumbnail = R.drawable.ic_launcher_background),
        Sound(id = 103, title = "Fan", resId = R.raw.fan_loop, thumbnail = R.drawable.ic_launcher_background),
        Sound(id = 104, title = "Deep Hum", resId = R.raw.deep_humming, thumbnail = R.drawable.ic_launcher_background),
        Sound(id = 105, title = "Heartbeat", resId = R.raw.heartbeat_loop, thumbnail = R.drawable.ic_launcher_background)
    )
}