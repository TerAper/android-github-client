package com.yourname.githubclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.yourname.githubclient.databinding.ActivityMainBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.util.ThemeManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Load theme before super.onCreate (keep this)
        runBlocking {
            val isDark = ServiceLocator.dataStore.themeFlow.first()
            ThemeManager.apply(isDark)
        }

        setTheme(R.style.Theme_GithubClient)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                val loggedIn = ServiceLocator.dataStore.isLoggedInFlow.first()
                val navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)

                val destination = if (loggedIn) R.id.mainFlowFragment else R.id.loginFragment

                // Try to remove previous stack OR duplicated directions
                try {
                    navController.popBackStack(R.id.auth_graph, true)
                } catch (_: Exception) {}

                if (navController.currentDestination?.id != destination) {
                    navController.navigate(destination)
                }
            }
        }
    }
}
