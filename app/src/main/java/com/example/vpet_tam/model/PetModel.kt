package com.example.vpet_tam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pet")
data class PetModel(
    @ColumnInfo(name = "name")
    var Petname: String,

    @ColumnInfo(name = "age")
    var Petage: String,

    @ColumnInfo(name = "hunger")
    var Pethunger: String,

    @ColumnInfo(name = "health")
    var Pethealth: String,

    @ColumnInfo(name = "energy")
    var Petenergy: String

    ) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null
}



