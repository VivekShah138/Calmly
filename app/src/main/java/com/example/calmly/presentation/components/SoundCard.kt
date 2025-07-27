package com.example.calmly.presentation.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.calmly.domain.local.model.Sound

@Composable
fun SoundCard(
    sound: Sound,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onPlayPauseClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = sound.thumbnail),
            contentDescription = sound.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = sound.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onPlayPauseClick) {
            if (isPlaying) {
                Icon(imageVector = Icons.Default.Pause, contentDescription = "Pause")
            } else {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play")
            }
        }
    }
}
