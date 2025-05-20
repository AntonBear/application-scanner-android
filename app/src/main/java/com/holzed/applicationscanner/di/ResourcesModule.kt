package com.holzed.applicationscanner.di

import com.holzed.applicationscanner.data.ResourcesProvider
import com.holzed.applicationscanner.data.ResourcesProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourcesModule {
    @Binds
    abstract fun bindResourcesProvider(impl: ResourcesProviderImpl): ResourcesProvider
}
