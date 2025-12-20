package com.aper.app.ui

data class AppUiState(
    val isToolbarVisible: Boolean = false,
    val toolbarTitle: String? = null,
    val isBackEnabled: Boolean = false,
    val isSettingsVisible: Boolean = false
)

