package com.yourname.githubclient.presentation.main.profile

import android.content.Intent
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.R
import com.yourname.githubclient.databinding.FragmentProfileBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import com.yourname.githubclient.util.ToolbarController
import com.yourname.githubclient.util.getColorFromAttr
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.Factory(
            ServiceLocator.logoutUseCase,
            ServiceLocator.saveAvatarUseCase,
            ServiceLocator.clearAvatarUseCase,
            ServiceLocator.getAvatarUseCase,
            ServiceLocator.getUsernameUseCase
        )
    }
    private val chooseImageLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                requireContext().contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.saveAvatar(it)
            }
        }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        (requireActivity() as ToolbarController).setToolbarTitle("Profile")
        setupToolbar()
        observeProfile()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logoutEvent.collect {
                    navigateToAuth()
                }
            }
        }


        binding.imgAvatar.setOnClickListener {
            chooseImageLauncher.launch(arrayOf("image/*"))
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }

    }

    private fun setupToolbar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_profile, menu)

                val item = menu.findItem(R.id.action_settings)
                item.icon?.setTint(
                    requireContext().getColorFromAttr(com.google.android.material.R.attr.colorOnSurface)
                )
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_settings -> {
                        findNavController().navigate(R.id.action_profile_to_settings)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    binding.tvUsername.text = viewModel.login
                    viewModel.avatarUri.collect { uri ->
                        val finalUri = uri?.toUri()
                            ?: "android.resource://${requireContext().packageName}/drawable/placeholder".toUri()

                        binding.imgAvatar.setImageURI(finalUri)

                        if (binding.imgAvatar.drawable == null) {
                            binding.imgAvatar.setImageResource(R.drawable.placeholder)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToAuth() {
        requireActivity()
            .findNavController(R.id.nav_host_fragment)
            .navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.auth_graph, true)
                    .build()
            )
    }
}
