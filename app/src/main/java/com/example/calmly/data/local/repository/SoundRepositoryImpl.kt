package com.example.calmly.data.local.repository

import android.content.Context
import com.example.calmly.data.local.data_source.SoundDataSource
import com.example.calmly.domain.local.model.Sound
import com.example.calmly.domain.local.repository.SoundRepository
import com.example.calmly.mapper.SoundMapper

class SoundRepositoryImpl(private val context: Context) : SoundRepository {
    override suspend fun getMeditationSounds(): List<Sound> {
        return SoundDataSource.meditationSounds.map {
            SoundMapper.responseToDomain(
                context,
                soundData = it
            )
        }
    }

    override suspend fun getSleepSounds(): List<Sound> {
        return SoundDataSource.sleepSounds.map {
            SoundMapper.responseToDomain(
                context,
                soundData = it
            )
        }
    }

    override suspend fun getAllSounds(): List<Sound> {
        return (SoundDataSource.meditationSounds + SoundDataSource.sleepSounds)
            .map {
                SoundMapper.responseToDomain(
                    context,
                    soundData = it
                )
            }
    }
}