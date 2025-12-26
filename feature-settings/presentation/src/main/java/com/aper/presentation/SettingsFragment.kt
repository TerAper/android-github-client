package com.aper.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.aper.core_android.ui.AppComposeTheme
import com.aper.core_android.ui.BaseComposeFragment
import com.aper.core_android.ui.LocalAppTheme
import com.aper.feature_settings.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseComposeFragment() {

    private val viewModel: SettingsViewModel by viewModels()

    private val toolbarController: com.aper.core_android.ui.ToolbarController?
        get() = activity as? com.aper.core_android.ui.ToolbarController

    private val bottomBarController: com.aper.core_android.ui.BottomBarController?
        get() = parentFragment?.parentFragment as? com.aper.core_android.ui.BottomBarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                AppTheme {
                    SettingsScreen(
                        onThemeSelected = viewModel::onThemeSelected
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbarAndBottomBar()
    }

    private fun setupToolbarAndBottomBar() {
        toolbarController?.apply {
            showToolbar()
            setToolbarTitle(getString(R.string.settings_title))
            setBackNavigationEnabled(true) {
                findNavController().popBackStack()
            }
        }
        bottomBarController?.hideBottomBar()
    }


}
