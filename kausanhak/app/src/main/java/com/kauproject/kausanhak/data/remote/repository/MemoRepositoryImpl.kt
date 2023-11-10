package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.db.MemoDAO
import com.kauproject.kausanhak.domain.model.entitiy.MemoEntity
import com.kauproject.kausanhak.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow

class MemoRepositoryImpl(
    private val memoDAO: MemoDAO
): MemoRepository {
    override suspend fun addMemo(memo: MemoEntity) {
        memoDAO.addMemo(memo)
    }

    override suspend fun deleteMemo(no: Int) {
        memoDAO.deleteMemo(no)
    }

    override fun readMemo(): Flow<List<MemoEntity>> {
        return memoDAO.readMemo()
    }
}