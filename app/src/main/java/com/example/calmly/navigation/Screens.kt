package com.example.calmly.navigation

import kotlinx.serialization.Serializable

sealed class Screens{
    @Serializable
    data object MeditationScreen: Screens()

    @Serializable
    data object SleepScreen: Screens()
}
