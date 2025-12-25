package com.aper.app_network.interceptor

import android.util.Log
import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
import com.aper.core_android.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    private val sessionData: AppSessionData,
    @ApplicationScope private val appScope: CoroutineScope
) : Interceptor {

    @Volatile
    private var token: String? = null

    init {
        appScope.launch {
            sessionData.observe(SessionDataKey.TokenKey)
                .collect { newToken ->
                    token = newToken
                }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentToken = token
        val request = chain.request()

        if (currentToken.isNullOrEmpty()) {
            return chain.proceed(request)
        }

        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", "token $currentToken")
                .build()
        )
    }
}
