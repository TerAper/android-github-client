package com.example.app_network.interceptor

import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    private val sessionData: AppSessionData
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var token: String?
        runBlocking {
            token = sessionData.observe(SessionDataKey.TokenKey).first()
        }
        token ?: return chain.proceed(request)

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "token $token")
            .build()

        return chain.proceed(newRequest)

    }
}

