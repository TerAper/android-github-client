package com.yourname.githubclient.presentation.main.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourname.githubclient.databinding.FragmentUsersBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import com.yourname.githubclient.util.ToolbarController
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment<FragmentUsersBinding, UsersViewModel>() {

    override val viewModel: UsersViewModel by viewModels {
        UsersViewModel.Factory(
            ServiceLocator.getUsersUseCase,
            ServiceLocator.startUsersSessionUseCase
        )
    }
    private lateinit var adapter: UsersAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersBinding =
        FragmentUsersBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        (requireActivity() as ToolbarController).setToolbarTitle("Users")
        setupRecycler()
        setupSwipeRefresh()
        observeUsers()
        viewModel.loadInitial()
    }

    private fun setupRecycler() {
        adapter = UsersAdapter { login, avatarUrl ->
            val action =
                UsersFragmentDirections.actionUsersFragmentToDetailsFragment(
                    login,
                    avatarUrl
                )
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UsersFragment.adapter

            binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val lm = recyclerView.layoutManager as LinearLayoutManager
                   val lastLastVisible = lm.findLastVisibleItemPosition()
                    val total = recyclerView.adapter?.itemCount ?: return

                    if (dy > 0 && lastLastVisible >= total - 5) {
                        viewModel.loadMore()
                    }
                }
            })
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { list ->
                    adapter.submitList(list)
                    binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        if (!binding.swipeRefresh.isRefreshing) {
            binding.progressBar.isVisible = isLoading
        }
    }
}
