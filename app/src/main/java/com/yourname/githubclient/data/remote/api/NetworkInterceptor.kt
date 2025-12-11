package com.yourname.githubclient.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {

    @Volatile
    private var token: String? = null

    fun setToken(t: String) {
        token = t
    }

    fun clearToken() {
        token = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        val t = token
        if (t == null)
            return chain.proceed(req)

        val newRequest = req.newBuilder()
            .addHeader("Authorization", "token $t")
            .build()

        return chain.proceed(newRequest)
    }
}
