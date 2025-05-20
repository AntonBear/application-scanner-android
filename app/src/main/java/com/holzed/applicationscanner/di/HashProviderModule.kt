package com.holzed.applicationscanner.di

import com.holzed.applicationscanner.data.HashProvider
import com.holzed.applicationscanner.data.HashProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HashProviderModule {

    @Binds
    abstract fun provideFileHash(impl: HashProviderImpl): HashProvider
}
