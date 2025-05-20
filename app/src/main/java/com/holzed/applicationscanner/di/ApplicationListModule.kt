package com.holzed.applicationscanner.di

import com.holzed.applicationscanner.data.ApplicationListProvider
import com.holzed.applicationscanner.data.ApplicationListProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ApplicationListModule {
    @Binds
    abstract fun bindApplicationListProvider(impl: ApplicationListProviderImpl): ApplicationListProvider
}
