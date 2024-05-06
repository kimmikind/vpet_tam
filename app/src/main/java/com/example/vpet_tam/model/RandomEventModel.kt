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

    @ColumnInfo(name = "ev_hunger")
    var Eventhunger: String,

    @ColumnInfo(name = "ev_health")
    var Eventhealth: String,

    @ColumnInfo(name = "ev_energy")
    var Eventenergy: String

    ) {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var Id: Int? = null
    }