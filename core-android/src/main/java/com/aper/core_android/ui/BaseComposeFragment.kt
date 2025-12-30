package com.aper.core_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aper.core_domain.model.AppTheme
import com.aper.core_domain.settings.AppSettingsData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseComposeFragment : Fragment() {

    @Inject lateinit var themeSettings: AppSettingsData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val theme by themeSettings
                    .observeTheme()
                    .collectAsStateWithLifecycle(
                        initialValue = AppTheme.SYSTEM
                    )

                AppComposeTheme(theme = theme) {
                    ScreenContent()
                }
            }
        }
    }

    @Composable
    protected abstract fun ScreenContent()
}
