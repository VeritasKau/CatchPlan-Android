package com.kauproject.kausanhak.domain.model.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_calendar_table")
data class EventDateEntity(
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