package com.aper.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.asState(
    scope: CoroutineScope,
    initial: T,
    timeoutMs: Long = 5_000
): StateFlow<T> =
    distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(timeoutMs),
            initialValue = initial
        )
