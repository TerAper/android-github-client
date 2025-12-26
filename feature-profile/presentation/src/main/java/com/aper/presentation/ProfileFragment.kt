package com.aper.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aper.core_android.ui.AppComposeTheme
import com.aper.core_android.ui.BottomBarController
import com.aper.core_android.ui.ToolbarController
import com.aper.presentation.event.ProfileUiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController
import androidx.core.net.toUri
import com.aper.core_android.ui.BaseComposeFragment
import com.aper.feature_profile.presentation.R


@AndroidEntryPoint
class ProfileFragment : BaseComposeFragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController


    private val bottomBarController: BottomBarController?
        get() = parentFragment?.parentFragment as? BottomBarController

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
                val username by viewModel.username.collectAsStateWithLifecycle()
                val avatarUri by viewModel.avatarUri.collectAsStateWithLifecycle()

                AppTheme {
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
        setupToolbarAndBottomBar()
        observeUiEvents()

    }

    private fun setupToolbarAndBottomBar() {
        toolbarController?.apply {
            showToolbar()
            setToolbarTitle(getString(R.string.profile_bar_title))
            setSettingsEnabled(
                enabled = true,
                onSettingsClicked = {
                    viewModel.onSettingsClick()
                }
            )
            bottomBarController?.showBottomBar()
        }
    }

    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    when (event) {
                        ProfileUiEvent.NavigateToSettings ->
                            findNavController().navigate(com.aper.feature_settings.presentation.R.id.setting_graph)

                        ProfileUiEvent.PickAvatar ->
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

}