package com.yourname.githubclient.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.yourname.githubclient.R
import com.yourname.githubclient.presentation.base.BaseViewModel

class MainFlowViewModel(private val state: SavedStateHandle) : BaseViewModel() {

    companion object {
        private const val KEY_SELECTED_TAB = "selected_tab"
    }

    val selectedTabId = state.getLiveData(KEY_SELECTED_TAB, R.id.profileFragment)

    fun setSelectedTab(tabId: Int) {
        state[KEY_SELECTED_TAB] = tabId
    }
}
