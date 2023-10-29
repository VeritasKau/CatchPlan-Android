package com.kauproject.kausanhak.data.module

import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.remote.repository.EventDateRepositoryImpl
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideEventDateRepository(eventDateDAO: EventDateDAO): EventDateRepository{
        return EventDateRepositoryImpl(eventDateDAO)
    }
}