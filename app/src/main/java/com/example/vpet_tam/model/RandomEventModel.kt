package com.example.vpet_tam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RandomEvent")
data class RandomEventModel(
    @ColumnInfo(name = "event")
    var Event: String,

    @ColumnInfo(name = "type")
    var Type: String,

    @ColumnInfo(name = "stat_type")
    var StatType: String,

    @ColumnInfo(name = "x_num")
    var XNumber: Double,

    @ColumnInfo(name = "ev_time")
    var EventTime: Int

    ) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var Id: Int? = null
    }