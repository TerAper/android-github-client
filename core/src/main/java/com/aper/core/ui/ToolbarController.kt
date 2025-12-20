package com.aper.core.ui

interface ToolbarController {
    fun showToolbar()
    fun hideToolbar()
    fun setToolbarTitle(title: String?)
    fun setBackNavigationEnabled(enabled: Boolean, onBackClicked: (() -> Unit)? = null)
    fun setSettingsEnabled(enabled: Boolean, onSettingsClicked: (() -> Unit)?)
}
