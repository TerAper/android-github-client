package com.aper.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.aper.app.databinding.ActivityMainBinding
import com.aper.core.model.AppTheme
import com.aper.core_android.ui.ToolbarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarController {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private var backClickListener: (() -> Unit)? = null
    private var settingsClickListener: (() -> Unit)? = null
    private var isAfterRecreation = true


    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        binding.toolbar.setNavigationOnClickListener {
            backClickListener?.invoke()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            android.util.Log.d(
                "NAV_TRACE",
                "Destination=${destination.displayName}, label=${destination.label}"
            )
        }
        decideStartDestination(savedInstanceState)
        observeTheme()
        observeToolbar()
    }

    private fun decideStartDestination(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.isLoggedInFlow.collect {
                if(savedInstanceState != null && isAfterRecreation){
                    isAfterRecreation = false
                    return@collect}
                navController.navigate(
                    if (it) R.id.mainFlowFragment else R.id.loginFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.nav_host_fragment, true)
                        .build()
                )
            }
        }
    }

    private fun observeTheme() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.themeFlow.collect { theme ->
                    applyTheme(theme)
                }
            }
        }
    }

    private fun observeToolbar() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.isToolbarVisible) supportActionBar?.show()
                    else supportActionBar?.hide()

                    supportActionBar?.title = state.toolbarTitle
                    supportActionBar?.setDisplayHomeAsUpEnabled(state.isBackEnabled)

                    invalidateOptionsMenu()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_settings)?.isVisible =
            viewModel.uiState.value.isSettingsVisible
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                settingsClickListener?.invoke()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showToolbar() = viewModel.showToolbar()
    override fun hideToolbar() = viewModel.hideToolbar()
    override fun setToolbarTitle(title: String?) = viewModel.setToolbarTitle(title)
    override fun setSettingsEnabled(enabled: Boolean, onSettingsClicked: (() -> Unit)?) {
        viewModel.setSettingsVisible(enabled)
        settingsClickListener = if (enabled) onSettingsClicked else null
    }

    override fun setBackNavigationEnabled(enabled: Boolean, onBackClicked: (() -> Unit)?) {
        viewModel.setBackEnabled(enabled)
        backClickListener = if (enabled) onBackClicked else null
    }

    private fun applyTheme(theme: AppTheme) {
        val mode = when (theme) {
            AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            AppTheme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

}

