package com.kauproject.kausanhak.data.module

import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.db.MemoDAO
import com.kauproject.kausanhak.data.db.ScrapDAO
import com.kauproject.kausanhak.data.remote.repository.EventDateRepositoryImpl
import com.kauproject.kausanhak.data.remote.repository.EventRepositoryImpl
import com.kauproject.kausanhak.data.remote.repository.MemoRepositoryImpl
import com.kauproject.kausanhak.data.remote.repository.PlaceEventRepositoryImpl
import com.kauproject.kausanhak.data.remote.repository.PromotionRepositoryImpl
import com.kauproject.kausanhak.data.remote.repository.ScrapRepositoryImpl
import com.kauproject.kausanhak.data.remote.response.GetPromotionResponse
import com.kauproject.kausanhak.data.remote.service.event.GetEventService
import com.kauproject.kausanhak.data.remote.service.event.GetPlaceEventService
import com.kauproject.kausanhak.data.remote.service.promotion.GetPromotionService
import com.kauproject.kausanhak.domain.repository.EventDateRepository
import com.kauproject.kausanhak.domain.repository.EventRepository
import com.kauproject.kausanhak.domain.repository.MemoRepository
import com.kauproject.kausanhak.domain.repository.PlaceEventRepository
import com.kauproject.kausanhak.domain.repository.PromotionRepository
import com.kauproject.kausanhak.domain.repository.ScrapRepository
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
    fun provideEventRepository(getEventService: GetEventService, getPromotionService: GetPromotionService): EventRepository{
        return EventRepositoryImpl(getEventService, getPromotionService)
    }

    @Singleton
    @Provides
    fun provideMemoRepository(memoDAO: MemoDAO): MemoRepository{
        return MemoRepositoryImpl(memoDAO)
    }

    @Singleton
    @Provides
    fun provideScrapRepository(scrapDAO: ScrapDAO): ScrapRepository{
        return ScrapRepositoryImpl(scrapDAO)
    }

    @Singleton
    @Provides
    fun providePromotionRepository(getPromotionService: GetPromotionService): PromotionRepository{
        return PromotionRepositoryImpl(getPromotionService)
    }

    @Singleton
    @Provides
    fun providePlaceEventRepository(getPlaceEventService: GetPlaceEventService): PlaceEventRepository{
        return PlaceEventRepositoryImpl(getPlaceEventService)
    }
}