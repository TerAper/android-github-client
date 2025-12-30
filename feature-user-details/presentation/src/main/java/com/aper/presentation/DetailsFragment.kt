package com.aper.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.aper.core_android.ui.BottomBarController
import com.aper.core_android.ui.ToolbarController
import com.aper.feature_user_details.presentation.R
import com.aper.feature_user_details.presentation.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    private val bottomBarController: BottomBarController?
        get() = parentFragment?.parentFragment as? BottomBarController

    private val viewModel: DetailsViewModel by viewModels()
    private val reposAdapter by lazy { DetailsAdapter() }

    private val login: String by lazy {
        requireArguments().getString("login").orEmpty()
    }

    private val avatarUrl: String by lazy {
        requireArguments().getString("avatarUrl").orEmpty()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)

        setupToolbarAndBottomBar()
        setupHeader()
        setupRecycler()
        observeState()

        viewModel.load(login)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.progress.isVisible = state.isLoading
                    binding.recyclerRepos.isVisible = !state.isLoading
                    reposAdapter.submitList(state.repos)
                }
            }
        }
    }

    private fun setupToolbarAndBottomBar() {
        toolbarController?.apply {
            showToolbar()
            setSettingsEnabled(false, null)
            setToolbarTitle(getString(R.string.details_title))
            setBackNavigationEnabled(true) {
                findNavController().popBackStack()
            }
        }
        bottomBarController?.hideBottomBar()
    }

    private fun setupHeader() {
        binding.tvUsername.text = login
        binding.avatar.load(avatarUrl) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
            fallback(R.drawable.ic_placeholder)
        }
    }

    private fun setupRecycler() {
        binding.recyclerRepos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reposAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}