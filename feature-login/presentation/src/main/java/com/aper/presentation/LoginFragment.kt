package com.aper.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aper.core_android.ui.BaseComposeFragment
import com.aper.core_android.ui.ToolbarController
import com.aper.feature_login.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment() {

    private val viewModel: LoginViewModel by viewModels()

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    @Composable
    override fun ScreenContent() {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val snackbarHostState = remember { SnackbarHostState() }
        val context = LocalContext.current

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

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    is LoginUiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            event.message.asString(context)
                        )
                    }
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
