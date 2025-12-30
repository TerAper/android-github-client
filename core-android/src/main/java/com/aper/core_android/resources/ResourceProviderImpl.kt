package com.aper.core_android.resources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ResourceProviderImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : ResourceProvider {

    override fun string(resId: Int, arg:Any): String {
        return context.getString(resId,arg)
    }

    override fun string(resId: Int, vararg args: Any): String {
        return context.getString(resId,args)
    }
}
