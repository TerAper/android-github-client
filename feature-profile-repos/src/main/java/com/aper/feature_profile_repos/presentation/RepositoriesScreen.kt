package com.aper.feature_profile_repos.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoriesScreen(
    viewModel: RepositoriesViewModel
) {
    val repos by viewModel.repositories
    val isRefreshing by viewModel.isRefreshing

    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = viewModel::refresh,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn {
            itemsIndexed(repos) { index, repo ->
                RepoItem(repo)

                // pagination trigger
                if (index == repos.lastIndex) {
                    LaunchedEffect(repos.size) {
                        viewModel.loadNextPage()
                    }
                }
            }
        }
    }
}
