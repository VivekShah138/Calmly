package com.example.calmly.domain.local.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Sound(
    val id: Int,
    val title: String,
    @RawRes val resId: Int,
    @DrawableRes val thumbnail: Int
)
