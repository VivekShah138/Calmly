package com.example.calmly.di

import com.example.calmly.data.local.repository.SoundRepositoryImpl
import com.example.calmly.domain.local.repository.SoundRepository
import com.example.calmly.domain.local.usecases.GetAllSoundsUseCase
import com.example.calmly.player.SoundPlayerManager
import com.example.calmly.presentation.features.view_songs.viewmodel.GetSoundViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { SoundPlayerManager.getInstance(get()) }
    single<SoundRepository> { SoundRepositoryImpl(get()) }
    single { GetAllSoundsUseCase(get()) }
    viewModel { GetSoundViewModel(get()) }

}