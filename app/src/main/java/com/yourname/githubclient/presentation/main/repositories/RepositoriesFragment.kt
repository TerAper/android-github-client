package com.yourname.githubclient.presentation.main.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourname.githubclient.databinding.FragmentRepositoriesBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment

class RepositoriesFragment :
    BaseFragment<FragmentRepositoriesBinding, RepositoriesViewModel>() {

    private val adapter = RepositoriesAdapter()

    override val viewModel: RepositoriesViewModel by viewModels {
        RepositoriesViewModel.Factory(ServiceLocator.getUserRepositoriesUseCase)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Repositories"

        binding.recyclerRepositories.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRepositories.adapter = adapter

        binding.recyclerRepositories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItems = layoutManager.itemCount

                // Load next page when user scrolls close to the bottom
                if (lastVisibleItem >= totalItems - 3) {
                    viewModel.loadNextPage()
                }
            }
        })

        viewModel.repositories.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        viewModel.loadNextPage() // load page 1
    }
}
