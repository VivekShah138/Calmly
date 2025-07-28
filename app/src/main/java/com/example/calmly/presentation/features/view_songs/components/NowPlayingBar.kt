package com.example.calmly.presentation.features.view_songs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calmly.R
import com.example.calmly.ui.theme.NowPlayingButton
import com.example.calmly.ui.theme.NowPlayingButtonText


@Composable
fun NowPlayingBar(
    soundName: String,
    soundThumbnailResId: Int,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit
) {
    Surface(
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Thumbnail Image
            Image(
                painter = painterResource(id = soundThumbnailResId),
                contentDescription = soundName,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Sound title text (weight to take remaining space)
            Text(
                text = soundName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        onPlayPauseClick()
                    }
            ) {
                IconButton(
                    onClick = onPlayPauseClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun NowPlayingPreview(){
    NowPlayingBar(
        soundName = "Forest",
        isPlaying = true,
        onPlayPauseClick = {

        },
        soundThumbnailResId = R.drawable.forest
    )
}