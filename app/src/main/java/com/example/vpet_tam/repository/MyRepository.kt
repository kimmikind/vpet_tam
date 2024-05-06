package com.example.vpet_tam.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.vpet_tam.model.PetModel
import com.example.vpet_tam.model.RandomEventModel
import com.example.vpet_tam.room.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MyRepository {

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

        fun getEvent(context: Context): LiveData<RandomEventModel>? {
            myDatabase = initializeDB(context)
            randomEventModel = myDatabase!!.getDao().getEvent()
            return randomEventModel
        }

        fun insertEvent(context: Context, event: String, type: String, ev_hunger: String, ev_health: String, ev_energy: String
        ) {
            myDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val eventDetails = RandomEventModel(event, type, ev_hunger, ev_health, ev_energy)
                myDatabase!!.getDao().InsertEvent(eventDetails)
            }

        }


    }
}