package com.kauproject.kausanhak.data.module

import android.app.Application
import androidx.room.Room
import com.kauproject.kausanhak.data.db.EventDateDAO
import com.kauproject.kausanhak.data.db.EventDateDB
import com.kauproject.kausanhak.data.db.MemoDAO
import com.kauproject.kausanhak.data.db.MemoDB
import com.kauproject.kausanhak.data.db.ScrapDAO
import com.kauproject.kausanhak.data.db.ScrapDB
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

    @Provides
    @Singleton
    fun provideEventDateDB(application: Application): EventDateDB{
        return Room.databaseBuilder(
            application,
            EventDateDB::class.java,
            "event_data_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMemoDAO(memoDB: MemoDB): MemoDAO{
        return memoDB.memoDAO()
    }

    @Provides
    @Singleton
    fun provideMemoDB(application: Application): MemoDB{
        return Room.databaseBuilder(
            application,
            MemoDB::class.java,
            "memo_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideScrapDAO(scrapDB: ScrapDB): ScrapDAO{
        return scrapDB.scrapDAO()
    }

    @Provides
    @Singleton
    fun provideScrapDB(application: Application): ScrapDB{
        return Room.databaseBuilder(
            application,
            ScrapDB::class.java,
            "scrap_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}