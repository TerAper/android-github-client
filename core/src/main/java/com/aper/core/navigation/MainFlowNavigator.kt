package com.aper.core.navigation

interface MainFlowNavigator {
    fun navigateToSettings()
    fun popBack()
    fun navigateToDetails(userName: String, avatarUrl: String)
}


