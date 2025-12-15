package com.yourname.githubclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.databinding.ActivityMainBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.main.MainFlowFragment
import com.yourname.githubclient.util.ThemeManager
import com.yourname.githubclient.util.ToolbarController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity(), ToolbarController {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        runBlocking {
            ThemeManager.apply(ServiceLocator.dataStore.themeFlow.first())
        }

        setTheme(R.style.Theme_GithubClient)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                ?.findNavController() ?: return

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> setToolbarVisible(false)
                else -> setToolbarVisible(true)
            }
        }

        if (savedInstanceState == null) {
            handleAutoLogin(navController)
        }
    }

    private fun handleAutoLogin(navController: NavController) {
        lifecycleScope.launch {
            val dataStore = ServiceLocator.dataStore

            val loggedIn = dataStore.isLoggedInFlow.first()
            val token = dataStore.token.first().orEmpty()

            ServiceLocator.interceptor.setToken(token)

            val target =
                if (loggedIn) R.id.mainFlowFragment
                else R.id.loginFragment

            navController.navigate(
                target,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.nav_host_fragment, true)
                    .build()
            )
        }
    }

    override fun setToolbarVisible(visible: Boolean) {
        if (visible) supportActionBar?.show()
        else supportActionBar?.hide()
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setBottomNavVisible(visible: Boolean) {
        val mainFlow =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                ?.childFragmentManager
                ?.fragments
                ?.filterIsInstance<MainFlowFragment>()
                ?.firstOrNull()

        mainFlow?.setBottomNavVisible(visible)
    }
}
