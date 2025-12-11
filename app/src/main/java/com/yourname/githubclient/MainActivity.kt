package com.yourname.githubclient

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.yourname.githubclient.databinding.ActivityMainBinding
import com.yourname.githubclient.di.ServiceLocator
import com.yourname.githubclient.util.ThemeManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // --- Apply saved theme BEFORE setContentView ---
        lifecycleScope.launch {
            val isDark = ServiceLocator.dataStore.themeFlow.first()
            ThemeManager.apply(isDark)
        }

        setTheme(R.style.Theme_GithubClient) // normal theme for splash
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        lifecycleScope.launch {
            ServiceLocator.dataStore.token.collect { savedToken ->
                if (!savedToken.isNullOrEmpty()) {
                    ServiceLocator.interceptor.setToken(savedToken)
                }
            }
        }

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        val appBarConfig = AppBarConfiguration(
            setOf(R.id.loginFragment, R.id.mainFlowFragment)
        )
        binding.mainToolbar.setupWithNavController(navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, _, _ ->
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val current = navHost.navController.currentDestination?.id

        menu.findItem(R.id.action_settings)?.isVisible =
            current != R.id.settingsFragment

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val navHost =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                navHost.navController.navigate(R.id.settingsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
    }
}
