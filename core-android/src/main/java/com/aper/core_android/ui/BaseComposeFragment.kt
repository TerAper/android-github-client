package com.aper.core_android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aper.core_domain.model.AppTheme
import com.aper.core_domain.settings.AppSettingsData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseComposeFragment : Fragment() {

    @Inject lateinit var themeSettings: AppSettingsData

    @Composable
    protected fun AppTheme(
        content: @Composable () -> Unit
    ) {
        val theme by themeSettings
            .observeTheme()
            .collectAsStateWithLifecycle(
                initialValue = AppTheme.SYSTEM
            )

        AppComposeTheme(
            theme = theme,
            content = content
        )
    }
}
