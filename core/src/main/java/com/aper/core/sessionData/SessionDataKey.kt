package com.aper.core.sessionData

import com.aper.core.model.AppTheme

sealed interface SessionDataKey<T> {
    object TokenKey : SessionDataKey<String>
    object LoginKey : SessionDataKey<String>
    object AvatarKey : SessionDataKey<String?>
    object IsLoggedInKey : SessionDataKey<Boolean>
    object ThemeKey : SessionDataKey<AppTheme>
}
