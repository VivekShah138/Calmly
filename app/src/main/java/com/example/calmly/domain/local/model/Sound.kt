package com.example.calmly.domain.local.model


data class Sound(
    val id: Int,
    val title: String,
    val resId: Int,
    val thumbnail: Int,
    val duration: Int,
    val fav: Boolean,
    val author: String
)
