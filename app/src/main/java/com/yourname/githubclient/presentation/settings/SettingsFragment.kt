package com.yourname.githubclient.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.R
import com.yourname.githubclient.databinding.FragmentSettingsBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModel: SettingsViewModel = SettingsViewModel(
        ServiceLocator.getThemeUseCase,
        ServiceLocator.updateThemeUseCase,
        ServiceLocator.logoutUseCase
    )

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewReady() {

        // Observe theme
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.isDark.collectLatest { isDark ->
                    if (binding.switchTheme.isChecked != isDark) {
                        binding.switchTheme.setOnCheckedChangeListener(null)
                        binding.switchTheme.isChecked = isDark
                        binding.switchTheme.setOnCheckedChangeListener { _, checked ->
                            viewModel.changeTheme(checked)
                        }
                    }
                }
            }
        }


        // Switch listener (optional, safe to set here because collector will detach when needed)
        binding.switchTheme.setOnCheckedChangeListener { _, checked ->
            viewModel.changeTheme(checked)
        }

        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }
    }
}
