package com.example.calmly.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Spa
import androidx.compose.ui.graphics.vector.ImageVector

data class TopLevelDestination(
    val screen: Screens,
    val title: String,
    val icon: ImageVector
)

val TOP_LEVEL_DESTINATION = listOf(
    TopLevelDestination(
        screen = Screens.MeditationScreen,
        title = "Meditation",
        icon = Icons.Filled.Spa
    ),
    TopLevelDestination(
        screen = Screens.SleepScreen,
        title = "Sleep",
        icon = Icons.Filled.NightsStay
    )
)


