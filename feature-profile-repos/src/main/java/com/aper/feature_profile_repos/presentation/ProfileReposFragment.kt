package com.aper.feature_profile_repos.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aper.core.ui.AppComposeTheme
import com.aper.core.ui.ToolbarController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepositoriesFragment @Inject constructor(
) : Fragment() {

    private val viewModel: RepositoriesViewModel by viewModels()
    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setContent {
                val theme by viewModel.selectedTheme.collectAsState()

                AppComposeTheme(theme = theme) {
                    RepositoriesScreen(viewModel)
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarController?.setToolbarTitle("ProfileRepos")
        toolbarController?.showToolbar()
    }
}
