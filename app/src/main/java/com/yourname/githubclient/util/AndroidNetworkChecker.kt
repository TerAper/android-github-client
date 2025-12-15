package com.yourname.githubclient.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface NetworkChecker {
    fun isOnline(): Boolean
}