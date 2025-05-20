package com.holzed.applicationscanner.data

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

interface ResourcesProvider {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String
}

@Singleton
class ResourcesProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ResourcesProvider {
    override fun getString(resId: Int): String { return context.getString(resId) }

    override fun getString(resId: Int, vararg formatArgs: Any): String = context.getString(resId, *formatArgs)
}
