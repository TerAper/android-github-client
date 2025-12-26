package com.aper.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aper.core_android.ui.AppComposeTheme
import com.aper.core_android.ui.BaseComposeFragment
import com.aper.core_android.ui.ToolbarController
import com.aper.feature_login.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment() {

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
            val snackbarHostState = remember { SnackbarHostState() }

            AppTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { padding ->
                    LoginScreen(
                        modifier = Modifier.padding(padding),
                        state = state,
                        onUsernameChange = viewModel::onUserNameChange,
                        onPasswordChange = viewModel::onPasswordChange,
                        onLoginClick = viewModel::login
                    )
                }
           }


            LaunchedEffect(state.errorMessage) {
                state.errorMessage?.let {
                    snackbarHostState.showSnackbar(it)
                    viewModel.clearError()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarController?.apply {
            showToolbar()
            setToolbarTitle(getString(R.string.login_bar_title))
        }
    }
}
