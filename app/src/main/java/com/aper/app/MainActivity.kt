package com.aper.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.aper.core.ui.ToolbarController
import com.aper.core.util.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarController {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private var backClickListener: (() -> Unit)? = null
    private var settingsClickListener: (() -> Unit)? = null
    private var isAfterRecreation = true


    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        toolbar.setNavigationOnClickListener {
            backClickListener?.invoke()
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
                    ThemeManager.apply(theme)
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

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_settings)?.isVisible =
            viewModel.uiState.value.isSettingsVisible
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
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

}

