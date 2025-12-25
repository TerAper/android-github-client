package com.aper.core.navigation

interface MainFlowNavigator {
    fun navigateToSettings()
    fun navigateToDetails(userName: String, avatarUrl: String)
}


