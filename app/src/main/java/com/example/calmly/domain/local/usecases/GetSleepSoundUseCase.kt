package com.example.calmly.domain.local.usecases

import com.example.calmly.domain.local.model.Sound
import com.example.calmly.domain.local.repository.SoundRepository

class GetSleepSoundUseCase (
    private val repository: SoundRepository
) {
    suspend operator fun invoke(): List<Sound> = repository.getSleepSounds()
}