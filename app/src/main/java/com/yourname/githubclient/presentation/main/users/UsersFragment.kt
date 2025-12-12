package com.yourname.githubclient.presentation.main.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourname.githubclient.databinding.FragmentUsersBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment<FragmentUsersBinding, UsersViewModel>() {

    override val viewModel: UsersViewModel by viewModels {
        UsersViewModel.Factory(
            ServiceLocator.getAllUsersUseCase,
            ServiceLocator.usersRepository
        )
    }

    private lateinit var adapter: UsersAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentUsersBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Users"
        setupRecycler()
        setupSwipeRefresh()
        observeUsers()
        viewModel.loadInitial()
    }

    private fun setupRecycler() {
        adapter = UsersAdapter { user ->
            val action = UsersFragmentDirections.actionUsersFragmentToDetailsFragment(user.username!!)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return
                val lm = rv.layoutManager as LinearLayoutManager
                val lastVisible = lm.findLastVisibleItemPosition()
                val total = adapter.itemCount
                if (lastVisible >= total - 5) {
                    viewModel.loadMore()
                }
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collectLatest { list ->
                adapter.submitList(list)
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }
}
