package com.aper.presentation

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aper.core_android.ui.BaseComposeFragment
import com.aper.feature_settings.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseComposeFragment() {

    private val viewModel: SettingsViewModel by viewModels()

    private val toolbarController: com.aper.core_android.ui.ToolbarController?
        get() = activity as? com.aper.core_android.ui.ToolbarController

    private val bottomBarController: com.aper.core_android.ui.BottomBarController?
        get() = parentFragment?.parentFragment as? com.aper.core_android.ui.BottomBarController

    @Composable
    override fun ScreenContent() {
        SettingsScreen(
            onThemeSelected = viewModel::onThemeSelected
        )
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
