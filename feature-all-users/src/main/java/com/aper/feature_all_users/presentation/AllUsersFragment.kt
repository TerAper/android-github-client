package com.aper.feature_all_users.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aper.core.navigation.MainFlowNavigator
import com.aper.core.ui.ToolbarController
import com.aper.feature_all_users.R
import com.aper.feature_all_users.databinding.FragmentAllUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllUsersFragment : Fragment(R.layout.fragment_all_users) {

    private val viewModel: AllUsersViewModel by viewModels()
    private lateinit var binding: FragmentAllUsersBinding
    private lateinit var adapter: AllUsersAdapter
    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    private val navigator: MainFlowNavigator?
        get() = parentFragment?.parentFragment as? MainFlowNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllUsersBinding.bind(view)
        toolbarController?.setToolbarTitle("AllUsers")
        toolbarController?.showToolbar()
        setupRecycler()
        setupSwipeRefresh()
        observeState()

        viewModel.loadInitial()
    }

    private fun setupRecycler() {
        adapter = AllUsersAdapter { login, avatarUrl ->
            navigator?.navigateToDetails(login, avatarUrl)
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                val lm = rv.layoutManager as LinearLayoutManager
                val lastVisible = lm.findLastVisibleItemPosition()
                if (dy > 0 && lastVisible >= adapter.itemCount - 5) {
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

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.users.collect { users ->
                        adapter.submitInitial(users)
                        binding.swipeRefresh.isRefreshing = false
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        binding.progressBar.isVisible = isLoading
                    }
                }
            }
        }
    }
}
