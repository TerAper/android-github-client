package com.aper.core_android.resources

interface ResourceProvider {
    fun string(resId: Int, arg: Any): String
    fun string(resId: Int, vararg args: Any): String
}
