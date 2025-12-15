package com.yourname.githubclient.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.yourname.githubclient.R
import com.yourname.githubclient.databinding.FragmentMainFlowBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class MainFlowFragment :
    BaseFragment<FragmentMainFlowBinding, MainFlowViewModel>() {

    override val viewModel: MainFlowViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainFlowBinding.inflate(inflater, container, false)

    fun setBottomNavVisible(visible: Boolean) {
        binding.bottomNav.visibility =
            if (visible) View.VISIBLE else View.GONE
    }

    override fun onViewReady() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.mainFlowNavHost)
                    as NavHostFragment

        val navController = navHostFragment.navController

        viewModel.selectedTabId.observe(viewLifecycleOwner) { tabId ->
            val current = navController.currentDestination?.id

            if (current == R.id.settingsFragment) return@observe

            if (binding.bottomNav.selectedItemId != tabId) {
                binding.bottomNav.selectedItemId = tabId
            }

            if (current != tabId) {
                navController.navigate(tabId)
            }
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            viewModel.setSelectedTab(item.itemId)
            true
        }
    }
}
