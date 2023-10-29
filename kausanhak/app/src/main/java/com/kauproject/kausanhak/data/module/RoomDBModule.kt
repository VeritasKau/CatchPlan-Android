package com.kauproject.kausanhak.data.module

import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.db.EventDateDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDBModule {
    @Provides
    @Singleton
    fun provideEventDateDAO(eventDateDB: EventDateDB): EventDateDAO{
        return eventDateDB.eventDateDAO()
    }
}