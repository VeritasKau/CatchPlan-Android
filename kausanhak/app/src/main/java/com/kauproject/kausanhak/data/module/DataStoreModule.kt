package com.kauproject.kausanhak.data.module

import android.content.Context
import com.kauproject.kausanhak.data.remote.repository.UserDataRepositoryImpl
import com.kauproject.kausanhak.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providerUserDataStore(
        @ApplicationContext context: Context
    ): UserDataRepository{
        return UserDataRepositoryImpl(context)
    }
}