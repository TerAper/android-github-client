package com.aper.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aper.core.navigation.MainFlowNavigator
import com.aper.feature_all_users.presentation.R
import com.aper.feature_all_users.presentation.databinding.FragmentAllUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllUsersFragment : Fragment(R.layout.fragment_all_users) {

    private val viewModel: AllUsersViewModel by viewModels()
    private lateinit var binding: FragmentAllUsersBinding
    private lateinit var adapter: AllUsersAdapter
    private val toolbarController: com.aper.core_android.ui.ToolbarController?
        get() = activity as? com.aper.core_android.ui.ToolbarController

    private val navigator: MainFlowNavigator?
        get() = parentFragment?.parentFragment as? MainFlowNavigator

    private val bottomBarController: com.aper.core_android.ui.BottomBarController?
        get() = parentFragment?.parentFragment as? com.aper.core_android.ui.BottomBarController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllUsersBinding.bind(view)

        setupToolbarAndBottomBar()
        setupRecycler()
        setupSwipeRefresh()
        observeState()
        viewModel.loadInitial()
    }

    private fun setupToolbarAndBottomBar(){
        toolbarController?.apply {
            showToolbar()
            setToolbarTitle(getString(R.string.toolbar_label))
            setSettingsEnabled(false,null)
            setBackNavigationEnabled(false,null)
        }
        bottomBarController?.showBottomBar()
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
                viewModel.uiState.collect { state ->

                    adapter.submitInitial(state.users)

                    binding.progressBar.isVisible = state.isLoading
                    binding.swipeRefresh.isRefreshing = state.isLoading

                }
            }
        }
    }
}
