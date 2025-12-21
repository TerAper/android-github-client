package com.aper.feature_login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aper.core.ui.AppComposeTheme
import com.aper.core.ui.ToolbarController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        setContent {
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            val theme by viewModel.selectedTheme.collectAsState()

            val snackbarHostState = remember { SnackbarHostState() }

            AppComposeTheme(theme = theme) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { padding ->
                    LoginScreen(
                        padding = padding,
                        state = state,
                        onLoginClick = viewModel::login

                    )
                }
            }

            LaunchedEffect(state.errorMessage) {
                state.errorMessage?.let { message ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Long
                    )
                    viewModel.clearError()
                }
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarController?.apply {
            showToolbar()
            setToolbarTitle("Login")
        }
    }
}
