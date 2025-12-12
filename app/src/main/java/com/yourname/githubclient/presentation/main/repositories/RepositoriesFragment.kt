package com.yourname.githubclient.presentation.main.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yourname.githubclient.databinding.FragmentRepositoriesBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class RepositoriesFragment :
    BaseFragment<FragmentRepositoriesBinding, RepositoriesViewModel>() {

    override val viewModel: RepositoriesViewModel by lazy { RepositoriesViewModel() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepositoriesBinding =
        FragmentRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        // TODO: logic later
    }
}
