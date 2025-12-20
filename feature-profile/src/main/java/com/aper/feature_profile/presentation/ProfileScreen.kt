package com.aper.feature_profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.aper.feature_profile.R

@Composable
fun ProfileScreen(
    username: String,
    avatarUri: String?,
    onAvatarClick: () -> Unit,
    onLogout: () -> Unit
) {
    val placeholderColor = MaterialTheme.colorScheme.onBackground // automatically adapts

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(32.dp))

        val avatarModel = avatarUri ?: R.drawable.ic_placeholder

        Image(
            painter = rememberAsyncImagePainter(avatarModel),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable(onClick = onAvatarClick),
            colorFilter = if (avatarUri == null) ColorFilter.tint(placeholderColor) else null
        )

        Spacer(Modifier.height(16.dp))

        Text(username, style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(32.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
