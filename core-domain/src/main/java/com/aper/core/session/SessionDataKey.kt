package com.aper.core.session

import kotlinx.coroutines.flow.Flow

sealed interface SessionDataKey<T> {

    val observe: (AppSessionData) -> Flow<T?>
    val save: suspend (AppSessionData, T) -> Unit

    data object TokenKey : SessionDataKey<String> {
        override val observe = { it: AppSessionData -> it.observeToken() }
        override val save: suspend (AppSessionData, String) -> Unit = { s: AppSessionData, v: String -> s.setToken(v) }
    }

    data object LoginKey : SessionDataKey<String> {
        override val observe = { it: AppSessionData -> it.observeLogin() }
        override val save: suspend (AppSessionData, String) -> Unit = { s: AppSessionData, v: String -> s.setLogin(v) }
    }

    data object AvatarKey : SessionDataKey<String?> {
        override val observe = { it: AppSessionData -> it.observeAvatar() }
        override val save: suspend (AppSessionData, String?) -> Unit = { s: AppSessionData, v: String? -> s.setAvatar(v) }
    }

    data object IsLoggedInKey : SessionDataKey<Boolean> {
        override val observe = { it: AppSessionData -> it.observeIsLoggedIn() }
        override val save: suspend (AppSessionData, Boolean) -> Unit = { s: AppSessionData, v: Boolean -> s.setIsLoggedIn(v) }
    }
}
