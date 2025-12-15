package com.yourname.githubclient.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.yourname.githubclient.MainActivity
import com.yourname.githubclient.R
import com.yourname.githubclient.databinding.FragmentDetailsBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Factory(
            ServiceLocator.getUserReposUseCaseForDetails
        )
    }

    private val args: DetailsFragmentArgs by navArgs()
    private val reposAdapter by lazy { DetailsAdapter() }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailsBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        binding.tvUsername.text = args.login
        binding.avatar.load(args.avatarUrl) {
            placeholder(R.drawable.ic_profile)
            error(R.drawable.ic_profile)
            fallback(R.drawable.ic_profile)
        }
        viewModel.load(args.login)
        setupBars()
        setupRecycler()
    }

    private fun setupBars() {
        (requireActivity() as MainActivity).setBottomNavVisible(false)
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(null)
        }
        binding.root.post {
            activity.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
                ?.setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
        }
    }

    private fun setupRecycler() {
        binding.recyclerRepos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRepos.adapter = reposAdapter
        lifecycleScope.launch {
            viewModel.repos.collectLatest { repos ->
                reposAdapter.submitList(repos)
            }
        }
    }

    override fun onDestroyView() {
        (requireActivity() as MainActivity).setBottomNavVisible(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
