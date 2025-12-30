package com.aper.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoriesScreen(
    state: ProfileReposUiState,
    onRefresh: () -> Unit,
    onLoadNext: () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        state = pullToRefreshState,
        onRefresh = onRefresh,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn {
            itemsIndexed(state.repos) { index, repo ->
                RepoItem(repo)

                if (index == state.repos.lastIndex && !state.isLoadingNext && state.hasMore) {
                    LaunchedEffect(state.repos.size) {
                        onLoadNext()
                    }
                }
            }
        }
    }
}
