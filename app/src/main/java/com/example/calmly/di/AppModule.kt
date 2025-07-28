package com.example.calmly.di

import com.example.calmly.player.SoundPlayerManager
import com.example.calmly.presentation.viewmodel.MeditationViewModel
import com.example.calmly.presentation.viewmodel.SharedPlayerViewModel
import com.example.calmly.presentation.viewmodel.SleepViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SoundPlayerManager.getInstance(get()) }
    viewModel{ MeditationViewModel(get()) }
    viewModel{ SleepViewModel(get()) }
    single { SharedPlayerViewModel(get()) }
}