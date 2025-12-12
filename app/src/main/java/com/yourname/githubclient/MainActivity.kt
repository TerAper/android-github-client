package com.yourname.githubclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yourname.githubclient.databinding.ActivityMainBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.presentation.main.MainFlowFragment
import com.yourname.githubclient.util.ThemeManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        runBlocking {
            val isDark = ServiceLocator.dataStore.themeFlow.first()
            ThemeManager.apply(isDark)
        }

        setTheme(R.style.Theme_GithubClient)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {

            lifecycleScope.launch {

                val loggedIn = ServiceLocator.dataStore.isLoggedInFlow.first()

                val navController = supportFragmentManager
                    .findFragmentById(R.id.nav_host_fragment)
                    ?.findNavController()
                    ?: return@launch

                val destination = if (loggedIn) {
                    R.id.mainFlowFragment
                } else {
                    R.id.loginFragment
                }

                try {
                    navController.popBackStack(R.id.auth_graph, true)
                } catch (_: Exception) {}

                if (navController.currentDestination?.id != destination) {
                    navController.navigate(destination)
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)
            ?.findNavController()
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
    fun setBottomNavEnabled(enabled: Boolean) {
        val mainFlowFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.filterIsInstance<MainFlowFragment>()
            ?.firstOrNull()

        mainFlowFragment?.setBottomNavEnabled(enabled)
    }
}
