package com.yourname.githubclient.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.MainActivity
import com.yourname.githubclient.R
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
        val activity = requireActivity() as AppCompatActivity

        // Use MainActivity toolbar
        activity.supportActionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
            // Set click listener for back arrow
            setHomeAsUpIndicator(null) // optional: use default arrow
        }

        // Handle the toolbar back arrow click
        binding.root.post {
            activity.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
                ?.setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
        }

        // Disable bottom nav while in settings
        (requireActivity() as MainActivity).setBottomNavEnabled(false)

        // Observe theme toggle
        observeTheme()
    }

    override fun onDestroyView() {
        // Re-enable bottom nav and hide back arrow
        (requireActivity() as MainActivity).setBottomNavEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
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
