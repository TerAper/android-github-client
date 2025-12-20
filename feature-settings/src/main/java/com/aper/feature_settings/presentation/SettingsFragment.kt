package com.aper.feature_settings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aper.core.ui.AppComposeTheme
import com.aper.core.ui.BottomBarController
import com.aper.core.ui.ToolbarController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    private val bottomBarController: BottomBarController?
        get() = parentFragment?.parentFragment as? BottomBarController

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
                val theme by viewModel.selectedTheme.collectAsState()
                AppComposeTheme(theme = theme) {
                    SettingsScreen(
                        selectedTheme = theme,
                        onThemeSelected = viewModel::onThemeSelected
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarController?.showToolbar()
        toolbarController?.setToolbarTitle("Settings")
        toolbarController?.setBackNavigationEnabled(true) {
            findNavController().popBackStack()
        }

        bottomBarController?.hideBottomBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarController?.setBackNavigationEnabled(false, null)
        bottomBarController?.showBottomBar()
    }
}
