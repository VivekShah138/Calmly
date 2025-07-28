package com.example.calmly.mapper

import android.content.Context
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.annotation.RawRes
import androidx.core.net.toUri
import com.example.calmly.data.local.data_source.SoundData
import com.example.calmly.domain.local.model.Sound
import kotlin.math.ceil

object SoundMapper {

    fun responseToDomain(context: Context, fav: Boolean = false, soundData: SoundData): Sound {
        val duration = getDurationFromRawRes(context, soundData.resId).toInt()
        val durationInSeconds = ceil(duration / 1000.0).toInt()
        Log.d("SoundMapper", "ID: ${soundData.id}, Title: ${soundData.title}, Duration(ms): $duration")
        Log.d("SoundMapper", "ID: ${soundData.id}, Title: ${soundData.title}, Duration(s): $durationInSeconds")
        return Sound(
            id = soundData.id,
            title = soundData.title,
            resId = soundData.resId,
            thumbnail = soundData.thumbnail,
            duration = durationInSeconds,
            fav = fav
        )
    }

    private fun getDurationFromRawRes(context: Context, @RawRes rawResId: Int): Long {
        return try {
            val uri = "android.resource://${context.packageName}/$rawResId".toUri()
            val retriever = MediaMetadataRetriever().apply {
                setDataSource(context, uri)
            }
            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            retriever.release()
            durationStr?.toLongOrNull() ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }
}
