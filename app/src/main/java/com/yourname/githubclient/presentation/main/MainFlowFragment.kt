package com.yourname.githubclient.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.yourname.githubclient.databinding.FragmentMainFlowBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class MainFlowFragment : BaseFragment<FragmentMainFlowBinding, MainFlowViewModel>() {

    override val viewModel: MainFlowViewModel by lazy { MainFlowViewModel() }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainFlowBinding.inflate(inflater, container, false)

    override fun onViewReady() {

        val args = MainFlowFragmentArgs.fromBundle(requireArguments())

        // Set username
        binding.tvUserName.text = args.login

        // Load avatar with Coil
        binding.ivAvatar.load(args.avatarUrl) {
            placeholder(android.R.drawable.sym_def_app_icon)
            error(android.R.drawable.ic_menu_report_image)
        }
    }
}
