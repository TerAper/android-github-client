package com.yourname.githubclient.presentation.main.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourname.githubclient.databinding.FragmentRepositoriesBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import com.yourname.githubclient.util.ToolbarController

class RepositoriesFragment :
    BaseFragment<FragmentRepositoriesBinding, RepositoriesViewModel>() {

    private val adapter = RepositoriesAdapter()

    override val viewModel: RepositoriesViewModel by viewModels {
        RepositoriesViewModel.Factory(
            ServiceLocator.getUserRepositoriesUseCase,
                ServiceLocator.getUsernameUseCase)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        (requireActivity() as ToolbarController).setToolbarTitle("Repositories")

        viewModel.repositories.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.swipeRefresh.isRefreshing = false
        }

        setupRecycler()
        setupSwipeRefresh()

        viewModel.loadInitial()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
    private fun setupRecycler() {
        binding.recyclerRepositories.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRepositories.adapter = adapter

        binding.recyclerRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val total = recyclerView.adapter?.itemCount ?: return

                if (lastVisibleItem >= total - 5) {
                    viewModel.loadNextPage()
                }
            }
        })
    }
}
