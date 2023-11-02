package com.kauproject.kausanhak.data.module

import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.remote.repository.EventDateRepositoryImpl
import com.kauproject.kausanhak.data.remote.repository.EventRepositoryImpl
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.EventRepository
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

    @Singleton
    @Provides
    fun provideEventRepository(getEventService: GetEventService): EventRepository{
        return EventRepositoryImpl(getEventService)
    }
}