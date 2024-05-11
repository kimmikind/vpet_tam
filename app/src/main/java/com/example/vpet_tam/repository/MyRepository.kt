package com.example.vpet_tam.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vpet_tam.model.PetModel
import com.example.vpet_tam.model.RandomEventModel
import com.example.vpet_tam.room.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

open class MyRepository {

    companion object {

        var myDatabase: MyDatabase? = null

        var petModel: LiveData<PetModel>? = null
        var randomEventModel: LiveData<RandomEventModel>? = null

        fun initializeDB(context: Context): MyDatabase {
            return MyDatabase.getDataseClient(context)
        }

        fun insertData(context: Context,
            name: String,
            age: String, hunger: String, health: String, energy: String
        ) {

            myDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                val petDetails = PetModel(name, age, hunger, health, energy)
                myDatabase!!.getDao().InsertData(petDetails)
            }

        }

        fun getPetDetails(context: Context, id: Int): LiveData<PetModel>? {
            myDatabase = initializeDB(context)
            petModel = myDatabase!!.getDao().getPetDetails(id)
            return petModel
        }

        fun deletePet(context: Context, id: Int) {
            myDatabase = initializeDB(context)
            myDatabase!!.getDao().deletePet(id)
        }

        fun getId(context: Context, name: String): LiveData<PetModel>? {
            myDatabase = initializeDB(context)
            petModel = myDatabase!!.getDao().getId(name)
            return petModel
        }

        fun selectAllEvents(): Int? {
            val count =  myDatabase!!.getDao().selectAllEvents()
            return count
        }

        fun getEvent(context: Context): LiveData<RandomEventModel>? {
            myDatabase = initializeDB(context)
            randomEventModel = myDatabase!!.getDao().getEvent()
            return randomEventModel
        }

        fun insertEvent(context: Context, event: String, type: String, stat_type: String, x_num: Double, ev_time :Int)
        {
            myDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val eventDetails = RandomEventModel(event, type, stat_type, x_num, ev_time)
                myDatabase!!.getDao().InsertEvent(eventDetails)
            }
        }

        fun updatePetName(context: Context, id: Int, name: String) {
            myDatabase = initializeDB(context)
            myDatabase!!.getDao().updatePetName(id,name)
        }

        fun updatePetStat(context: Context, id: Int, hunger: String, health: String, energy: String) {

            myDatabase = initializeDB(context)
            myDatabase!!.getDao().updatePetStat(id,hunger, health, energy)

        }



    }
}