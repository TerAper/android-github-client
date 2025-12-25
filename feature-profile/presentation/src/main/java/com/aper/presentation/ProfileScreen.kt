package com.aper.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.aper.feature_profile.presentation.R

@Composable
fun ProfileScreen(
    username: String,
    avatarUri: String?,
    onAvatarClick: () -> Unit,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    onClick = onAvatarClick
                ),
            colorFilter = if (avatarUri == null)
                ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            else null
        )

        Spacer(Modifier.height(16.dp))

        Text(username, style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(32.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
