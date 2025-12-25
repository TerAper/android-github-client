package com.aper.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aper.core_android.ui.AppComposeTheme
import com.aper.core_android.ui.BottomBarController
import com.aper.core_android.ui.ToolbarController
import com.aper.feature_profile_repos.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileReposFragment : Fragment() {

    private val viewModel: ProfileReposViewModel by viewModels()

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    private val bottomBarController: BottomBarController?
        get() = parentFragment?.parentFragment as? BottomBarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                val theme by viewModel.selectedTheme.collectAsStateWithLifecycle()
                val state by viewModel.uiState.collectAsStateWithLifecycle()

                AppComposeTheme(theme = theme) {
                    RepositoriesScreen(
                        state = state,
                        onRefresh = viewModel::refresh,
                        onLoadNext = viewModel::loadNextPage
                    )
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarAndBottomBar()
        viewModel.loadInitial()

    }

    private fun setupToolbarAndBottomBar() {
        bottomBarController?.showBottomBar()
        toolbarController?.apply {
            setToolbarTitle(getString(R.string.tool_bar_label))
            setSettingsEnabled(false, null)
            setBackNavigationEnabled(false, null)
            showToolbar()
        }
    }

}
