package com.aper.core.sessionData

import com.aper.core.model.AppTheme

sealed interface SessionDataKey<T> {
    data object TokenKey : SessionDataKey<String>
    data object LoginKey : SessionDataKey<String>
    data object AvatarKey : SessionDataKey<String?>
    data object IsLoggedInKey : SessionDataKey<Boolean>
    data object ThemeKey : SessionDataKey<AppTheme>
}
