package com.kauproject.kausanhak.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kauproject.kausanhak.domain.model.entitiy.MemoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDAO {
    @Query("SELECT * FROM memo_table")
    fun readMemo(): Flow<List<MemoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMemo(memoEntity: MemoEntity)

    @Query("DELETE FROM memo_table WHERE `no` = :no")
    suspend fun deleteMemo(no: Int)
}