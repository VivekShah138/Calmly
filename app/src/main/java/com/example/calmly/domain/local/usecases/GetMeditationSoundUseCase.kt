package com.example.calmly.domain.local.usecases

import com.example.calmly.domain.local.model.Sound
import com.example.calmly.domain.local.repository.SoundRepository

class GetMeditationSoundUseCase (
    private val repository: SoundRepository
) {
    suspend operator fun invoke(): List<Sound> = repository.getMeditationSounds()
}