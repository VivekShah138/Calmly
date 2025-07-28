package com.example.calmly.domain.local.usecases

data class GetSoundUseCaseWrapper (
    val getAllSoundsUseCase: GetAllSoundsUseCase,
    val getMeditationSoundUseCase: GetMeditationSoundUseCase,
    val getSleepSoundUseCase: GetSleepSoundUseCase
)