package com.kauproject.kausanhak.domain.repository

import com.kauproject.kausanhak.domain.model.entitiy.MemoEntity
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    suspend fun addMemo(memo: MemoEntity)
    suspend fun deleteMemo(no: Int)
    fun readMemo(): Flow<List<MemoEntity>>
}