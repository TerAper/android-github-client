package com.yourname.githubclient.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.databinding.FragmentSettingsBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels {
        SettingsViewModel.Factory(
            ServiceLocator.getThemeUseCase,
            ServiceLocator.updateThemeUseCase
        )
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        setupToolbar()
        observeTheme()
    }

    private fun setupToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.settingsToolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.settingsToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeTheme() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDark.collectLatest { isDark ->

                    binding.switchTheme.setOnCheckedChangeListener(null)

                    if (binding.switchTheme.isChecked != isDark) {
                        binding.switchTheme.isChecked = isDark
                    }

                    binding.switchTheme.setOnCheckedChangeListener { _, checked ->
                        viewModel.changeTheme(checked)
                    }
                }
            }
        }
    }
}
