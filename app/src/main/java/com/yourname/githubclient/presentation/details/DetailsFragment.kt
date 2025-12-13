package com.yourname.githubclient.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.yourname.githubclient.databinding.FragmentDetailsBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory(
            ServiceLocator.getUserDetailsUseCase,
            ServiceLocator.getUserReposUseCaseForDetails
        )
    }

    private val args: DetailsFragmentArgs by navArgs()

    private val reposAdapter by lazy { RepoAdapter() }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailsBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        setupToolbar()
        binding.recyclerRepos.layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerRepos.adapter = reposAdapter

        viewModel.load(args.username)

        observe()
    }

    private fun setupToolbar() {
        requireActivity().title = "Details"
    }

    private fun observe() {
        // Observe user details
        lifecycleScope.launch {
            viewModel.details.collectLatest { d ->
                d ?: return@collectLatest

                binding.avatar.load(d.avatarUrl)
                binding.tvUsername.text = d.username
            }
        }

        // Observe repositories list
        lifecycleScope.launch {
            viewModel.repos.collectLatest { repos ->
                reposAdapter.submitList(repos)
            }
        }
    }
}
