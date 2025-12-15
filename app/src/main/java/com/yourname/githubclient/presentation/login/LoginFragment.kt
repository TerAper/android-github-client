package com.yourname.githubclient.presentation.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.MainActivity
import com.yourname.githubclient.databinding.FragmentLoginBinding
import com.yourname.githubclient.presentation.base.BaseFragment
import com.yourname.githubclient.di.ServiceLocator
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels {
        Factory(ServiceLocator.loginUseCase)
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewReady() {
        listeners()
        navigating()
    }

    override fun handleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !isLoading
    }

    private fun navigating(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginSuccess.collect {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginToMainFlow()
                    )
                }
            }
        }
    }
    private fun listeners(){
        binding.btnLogin.setOnClickListener {
            val input = binding.etUsername.text.toString().trim()
            val token = binding.etToken.text.toString().trim()
            viewModel.login(input, token)
        }
    }

}
