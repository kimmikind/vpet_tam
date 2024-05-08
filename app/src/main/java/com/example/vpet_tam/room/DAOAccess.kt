package com.example.vpet_tam.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vpet_tam.model.PetModel
import com.example.vpet_tam.model.RandomEventModel

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(petModel: PetModel)

     @Query("SELECT * FROM Pet WHERE id =:id")
     fun getPetDetails(id: Int): LiveData<PetModel>

     @Query("DELETE FROM Pet WHERE id =:id")
     fun deletePet(id: Int)

     @Query("UPDATE Pet SET hunger =:hunger, health =:health, energy =:energy WHERE id =:id")
     fun updatePetStat(id: Int, hunger: String, health: String, energy: String)

     @Query("UPDATE Pet SET name =:name WHERE id =:id")
     fun updatePetName(id: Int, name: String)

     //получить событие рандомно
     @Query("SELECT * FROM RandomEvent ORDER BY RANDOM() LIMIT 1")
     fun getEvent() : LiveData<RandomEventModel>?

     //добавить событие
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun InsertEvent(randomEventModel: RandomEventModel)

     @Query("SELECT * FROM Pet WHERE name =:name")
     fun getId(name: String): LiveData<PetModel>?

}