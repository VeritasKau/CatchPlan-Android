package com.kauproject.kausanhak.domain.model.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "memo_table")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    var no: Int = 0,
    @ColumnInfo
    var date: String,
    @ColumnInfo
    var content: String
)