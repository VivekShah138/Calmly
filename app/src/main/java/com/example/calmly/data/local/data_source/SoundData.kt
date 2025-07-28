package com.example.calmly.data.local.data_source

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class SoundData(
    val id: Int,
    val title: String,
    @RawRes val resId: Int,
    @DrawableRes val thumbnail: Int
)
