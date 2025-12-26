package com.aper.app

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aper.app.databinding.FragmentMainFlowBinding
import com.aper.core_android.ui.BottomBarController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFlowFragment :
    Fragment(R.layout.fragment_main_flow),
    BottomBarController {

    private lateinit var binding: FragmentMainFlowBinding

    private val navController by lazy {
        (childFragmentManager
            .findFragmentById(R.id.mainFlowNavHost) as NavHostFragment)
            .navController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainFlowBinding.bind(view)

        binding.bottomNav.setupWithNavController(navController)

    }

    override fun showBottomBar() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    override fun hideBottomBar() {
        binding.bottomNav.visibility = View.GONE
    }
}
