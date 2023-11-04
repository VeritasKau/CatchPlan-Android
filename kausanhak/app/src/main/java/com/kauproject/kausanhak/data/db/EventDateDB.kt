package com.kauproject.kausanhak.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kauproject.kausanhak.data.model.EventDateEntity

@Database(entities = [EventDateEntity::class], version = 6, exportSchema = false)
abstract class EventDateDB: RoomDatabase() {
    abstract fun eventDateDAO(): EventDateDAO
}