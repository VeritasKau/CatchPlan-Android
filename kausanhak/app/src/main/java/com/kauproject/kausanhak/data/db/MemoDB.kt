package com.kauproject.kausanhak.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kauproject.kausanhak.domain.model.entitiy.MemoEntity

@Database(entities = [MemoEntity::class], version = 2, exportSchema = false)
abstract class MemoDB: RoomDatabase() {
    abstract fun memoDAO(): MemoDAO
}