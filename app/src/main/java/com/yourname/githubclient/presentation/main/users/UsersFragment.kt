package com.yourname.githubclient.presentation.main.users

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yourname.githubclient.databinding.FragmentUsersBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class UsersFragment : BaseFragment<FragmentUsersBinding, UsersViewModel>() {

    override val viewModel: UsersViewModel by lazy { UsersViewModel() }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersBinding =
        FragmentUsersBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        // TODO: logic later
    }
}
