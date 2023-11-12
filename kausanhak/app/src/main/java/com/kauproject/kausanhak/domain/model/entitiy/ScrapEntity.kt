package com.kauproject.kausanhak.domain.model.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scrap_table")
data class ScrapEntity(
    @PrimaryKey
    @ColumnInfo(name = "eventId")
    var eventId: Int,
    @ColumnInfo
    var date: String,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var place: String,
    @ColumnInfo
    var image: String
)