package com.example.calmly.data.local.data_source

import com.example.calmly.domain.local.model.Sound
import com.example.calmly.R



object SoundDataSource {

    val meditationSounds = listOf(
        SoundData(
            id = 1,
            title = "Whispers of the Forest",
            resId = R.raw.morning_forest_ambiance,
            thumbnail = R.drawable.forest,
            author = "Ava Linden"
        ),
        SoundData(
            id = 2,
            title = "Rainfall Serenity",
            resId = R.raw.real_rain_sound,
            thumbnail = R.drawable.rain,
            author = "Ava Linden"
        ),
        SoundData(
            id = 3,
            title = "Crackling Campfire",
            resId = R.raw.campfire,
            thumbnail = R.drawable.campfire,
            author = "Ava Linden"
        ),
        SoundData(
            id = 4,
            title = "Ocean Embrace",
            resId = R.raw.ocean_waves,
            thumbnail = R.drawable.sea,
            author = "Ava Linden"
        ),
        SoundData(
            id = 5,
            title = "Desert Wind Echoes",
            resId = R.raw.desert_wind,
            thumbnail = R.drawable.wind,
            author = "Ava Linden"
        ),
    )

    val sleepSounds = listOf(
        SoundData(
            id = 101,
            title = "Gentle White Noise",
            resId = R.raw.white_noise,
            thumbnail = R.drawable.white_noise,
            author = "Dr. Elias Monroe"
        ),
        SoundData(
            id = 102,
            title = "Soothing Lullaby",
            resId = R.raw.short_lullaby_song,
            thumbnail = R.drawable.lullaby,
            author = "Dr. Elias Monroe"
        ),
        SoundData(
            id = 103,
            title = "Calming Fan Breeze",
            resId = R.raw.fan_loop,
            thumbnail = R.drawable.ceiling_fan,
            author = "Dr. Elias Monroe"
        ),
        SoundData(
            id = 104,
            title = "Deep Humming Calm",
            resId = R.raw.deep_humming,
            thumbnail = R.drawable.humming,
            author = "Dr. Elias Monroe"
        ),
        SoundData(
            id = 105,
            title = "Heartbeat Comfort",
            resId = R.raw.heartbeat_loop,
            thumbnail = R.drawable.heartbeat,
            author = "Dr. Elias Monroe"
        ),
    )

}