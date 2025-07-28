package com.example.calmly.data.local.data_source

import com.example.calmly.domain.local.model.Sound
import com.example.calmly.R



object SoundDataSource {

    val meditationSounds = listOf(
        SoundData(id = 1, title = "Forest", resId = R.raw.morning_forest_ambiance,R.drawable.forest),
        SoundData(id = 2, title = "Rain", resId = R.raw.real_rain_sound,R.drawable.rain),
        SoundData(id = 3, title = "Campfire", resId = R.raw.campfire,R.drawable.campfire),
        SoundData(id = 4, title = "Ocean", resId = R.raw.ocean_waves,R.drawable.sea),
        SoundData(id = 5, title = "Wind", resId = R.raw.desert_wind,R.drawable.wind),
    )

    val sleepSounds = listOf(
        SoundData(id = 101, title = "White Noise", resId = R.raw.white_noise, thumbnail = R.drawable.white_noise),
        SoundData(id = 102, title = "Lullaby", resId = R.raw.short_lullaby_song, thumbnail = R.drawable.lullaby),
        SoundData(id = 103, title = "Fan", resId = R.raw.fan_loop, thumbnail = R.drawable.ceiling_fan),
        SoundData(id = 104, title = "Deep Hum", resId = R.raw.deep_humming, thumbnail = R.drawable.humming),
        SoundData(id = 105, title = "Heartbeat", resId = R.raw.heartbeat_loop, thumbnail = R.drawable.heartbeat)
    )
}