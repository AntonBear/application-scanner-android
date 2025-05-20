package com.holzed.applicationscanner.di

import android.content.Context
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {
    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }
}
