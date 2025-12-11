package com.yourname.githubclient.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yourname.githubclient.databinding.FragmentDetailsBinding
import com.yourname.githubclient.presentation.base.BaseFragment

class DetailsFragment(override val viewModel: DetailsViewModel) :
    BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding {
        TODO("Not yet implemented")
    }
}