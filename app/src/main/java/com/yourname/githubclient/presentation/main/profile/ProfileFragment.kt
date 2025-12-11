package com.yourname.githubclient.presentation.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yourname.githubclient.databinding.FragmentProfileBinding
import com.yourname.githubclient.presentation.base.BaseFragment
import com.yourname.githubclient.presentation.login.LoginViewModel

class ProfileFragment(override val viewModel: ProfileViewModel) :BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        TODO("Not yet implemented")
    }
}