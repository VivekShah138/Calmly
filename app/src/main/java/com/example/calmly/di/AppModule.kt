package com.example.calmly.di

import com.example.calmly.player.SoundPlayerManager
import com.example.calmly.presentation.viewmodel.MeditationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SoundPlayerManager.getInstance(get()) }
    viewModel{ MeditationViewModel(get()) }
}