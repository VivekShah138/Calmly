package com.example.calmly.presentation.features.view_songs.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calmly.R
import com.example.calmly.domain.local.model.Sound


//@Composable
//fun SoundCard(
//    sound: Sound,
//    isPlaying: Boolean,
//    onPlayPauseClick: () -> Unit,
//    onFavoriteClick: () -> Unit = {}
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(MaterialTheme.colorScheme.surface)
//            .clickable { onPlayPauseClick() }
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Image(
//            painter = painterResource(id = sound.thumbnail),
//            contentDescription = sound.title,
//            modifier = Modifier
//                .size(64.dp)
//                .clip(RoundedCornerShape(12.dp)),
//            contentScale = ContentScale.Crop
//        )
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        Column(
//            modifier = Modifier.weight(1f),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = sound.title,
//                style = MaterialTheme.typography.titleMedium,
//            )
//            DurationBadge(sound.duration)
//        }
//
//        IconButton(onClick = onPlayPauseClick) {
//            if (isPlaying) {
//                Icon(
//                    imageVector = Icons.Default.Pause,
//                    contentDescription = "Pause",
//                    modifier = Modifier.size(32.dp)
//                )
//            } else {
//                Icon(
//                    imageVector = Icons.Default.PlayArrow,
//                    contentDescription = "Play",
//                    modifier = Modifier.size(32.dp)
//                )
//            }
//        }
//
//        IconButton(onClick = onFavoriteClick) {
//            val icon = if (sound.fav) Icons.Filled.Favorite else Icons.Default.FavoriteBorder
//            Icon(
//                imageVector = icon,
//                contentDescription = if (sound.fav) "Unfavorite" else "Favorite"
//            )
//        }
//    }
//}

@Composable
fun SoundCard(
    sound: Sound,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onFavoriteClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onPlayPauseClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = sound.thumbnail),
            contentDescription = sound.title,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = sound.title,
                style = MaterialTheme.typography.titleMedium,
            )
            AuthorBadge(sound.author)
            DurationBadge(sound.duration)
        }

        IconButton(onClick = onPlayPauseClick) {
            if (isPlaying) {
                Icon(
                    imageVector = Icons.Default.Pause,
                    contentDescription = "Pause",
                    modifier = Modifier.size(32.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        IconButton(onClick = onFavoriteClick) {
            val icon = if (sound.fav) Icons.Filled.Favorite else Icons.Default.FavoriteBorder
            Icon(
                imageVector = icon,
                contentDescription = if (sound.fav) "Unfavorite" else "Favorite"
            )
        }
    }
}


@Preview
@Composable
fun SoundCardPreview(){
    SoundCard(
        Sound(
            id = 1,
            title = "Forest",
            resId =  R.raw.campfire,
            thumbnail = R.drawable.campfire,
            duration = 122,
            fav = true,
            author = "Vivek Shah"
        ),
        isPlaying = true,
        onPlayPauseClick = {

        }
    )
}

