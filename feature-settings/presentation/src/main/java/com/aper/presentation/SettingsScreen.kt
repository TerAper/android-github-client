package com.aper.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aper.core_android.ui.LocalAppTheme
import com.aper.core.model.AppTheme
import com.aper.feature_settings.presentation.R

@Composable
fun SettingsScreen(
    onThemeSelected: (AppTheme) -> Unit
) {
    val selectedTheme = LocalAppTheme.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.settings_theme),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

        ThemeItem(
            title = stringResource(R.string.theme_system),
            selected = selectedTheme == AppTheme.SYSTEM,
            onClick = { onThemeSelected(AppTheme.SYSTEM) }
        )

        ThemeItem(
            title = stringResource(R.string.theme_light),
            selected = selectedTheme == AppTheme.LIGHT,
            onClick = { onThemeSelected(AppTheme.LIGHT) }
        )

        ThemeItem(
            title = stringResource(R.string.theme_dark),
            selected = selectedTheme == AppTheme.DARK,
            onClick = { onThemeSelected(AppTheme.DARK) }
        )
    }
}

@Composable
private fun ThemeItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground
        )
        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}
