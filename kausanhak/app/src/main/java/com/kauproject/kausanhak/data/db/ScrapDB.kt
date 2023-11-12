package com.kauproject.kausanhak.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kauproject.kausanhak.domain.model.entitiy.ScrapEntity

@Database(entities = [ScrapEntity::class], version = 1, exportSchema = false)
abstract class ScrapDB: RoomDatabase() {
    abstract fun scrapDAO(): ScrapDAO
}