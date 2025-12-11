package com.yourname.githubclient.presentation.main.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yourname.githubclient.databinding.FragmentRepositoriesBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class RepositoriesFragment(override val viewModel: RepositoriesViewModel) :
    BaseFragment<FragmentRepositoriesBinding, RepositoriesViewModel>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepositoriesBinding {
        TODO("Not yet implemented")
    }
}