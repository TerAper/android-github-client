package com.aper.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aper.core_android.ui.BottomBarController
import com.aper.core_android.ui.ToolbarController
import com.aper.feature_all_users.presentation.R
import com.aper.feature_all_users.presentation.databinding.FragmentAllUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllUsersFragment : Fragment(R.layout.fragment_all_users) {

    private val viewModel: AllUsersViewModel by viewModels()
    private lateinit var binding: FragmentAllUsersBinding
    private lateinit var adapter: AllUsersAdapter

    private val toolbarController: ToolbarController?
        get() = activity as? ToolbarController

    private val bottomBarController: BottomBarController?
        get() = parentFragment?.parentFragment as? BottomBarController

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
            setToolbarTitle(getString(R.string.all_users_title))
            setSettingsEnabled(false,null)
            setBackNavigationEnabled(false,null)
        }
        bottomBarController?.showBottomBar()
    }

    private fun setupRecycler() {
        adapter = AllUsersAdapter { login, avatarUrl ->
            val bundle = bundleOf(
                "login" to login,
                "avatarUrl" to avatarUrl
            )

            findNavController().navigate(
                com.aper.feature_user_details.presentation.R.id.user_details_graph,
                bundle
            )
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
