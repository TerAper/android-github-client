package com.aper.feature_profile.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.aper.core.navigation.MainFlowNavigator
import com.aper.core.ui.AppComposeTheme
import com.aper.core.ui.ToolbarController
import com.aper.feature_profile.data.event.ProfileEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    private val navigator: MainFlowNavigator?
        get() = parentFragment?.parentFragment as? MainFlowNavigator

    private val pickImageLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            uri?.let {
                requireContext().contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.onAvatarPicked(it)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )

            setContent {
                val username by viewModel.username.collectAsState()
                val avatarUri by viewModel.avatarUri.collectAsState()
                val theme by viewModel.selectedTheme.collectAsState()

                AppComposeTheme(theme = theme) {
                    ProfileScreen(
                        username = username ?: "Unknown",
                        avatarUri = avatarUri,
                        onAvatarClick = viewModel::onAvatarClick,
                        onLogout = viewModel::logout
                    )
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarController?.apply {
            showToolbar()
            setToolbarTitle("Profile")

            setSettingsEnabled(
                enabled = true,
                onSettingsClicked = {
                    viewModel.onSettingsClick()
                }
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    ProfileEvent.NavigateToSettings -> {
                        navigator?.navigateToSettings()
                    }

                    ProfileEvent.PickAvatar -> {
                        openImagePicker()
                    }
                }
            }
        }
    }

    private fun openImagePicker() {
        pickImageLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        toolbarController?.apply {
            setSettingsEnabled(false, null)
        }
    }


}