package com.example.calmly.domain.local.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Sound(
    val id: Int,
    val title: String,
    val resId: Int,
    val thumbnail: Int,
    val duration: Int,
    val fav: Boolean
)
