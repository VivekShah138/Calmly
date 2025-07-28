package com.example.calmly.domain.local.repository

import com.example.calmly.domain.local.model.Sound

interface SoundRepository {
    suspend fun getMeditationSounds(): List<Sound>
    suspend fun getSleepSounds(): List<Sound>
    suspend fun getAllSounds(): List<Sound>
}