package com.yourname.githubclient.presentation.main.users

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yourname.githubclient.databinding.FragmentUsersBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class UsersFragment(override val viewModel: UsersViewModel) : BaseFragment<FragmentUsersBinding, UsersViewModel>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUsersBinding {
        TODO("Not yet implemented")
    }
}