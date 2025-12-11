package com.yourname.githubclient.presentation.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.databinding.FragmentLoginBinding
import com.yourname.githubclient.presentation.base.BaseFragment
import com.yourname.githubclient.di.ServiceLocator
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by lazy {
        LoginViewModelFactory(ServiceLocator.loginUseCase)
            .create(LoginViewModel::class.java)
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewReady() {

        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etToken.text.toString().trim())
        }

        // Observe login success using Flow
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginSuccess.collect { user ->
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToMainFlowFragment(
                            login = user.username,
                            avatarUrl = user.avatarUrl
                        )
                    )
                }
            }
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
    }
}
