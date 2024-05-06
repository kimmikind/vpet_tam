package com.example.vpet_tam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.vpet_tam.model.PetModel
import com.example.vpet_tam.model.RandomEventModel
import com.example.vpet_tam.repository.MyRepository

// избежание утечки памяти
class DbViewModel(application: Application) : AndroidViewModel(application) {

    private var _liveDataPet: LiveData<PetModel>? = null
    val liveDataPet: LiveData<PetModel>? get() = _liveDataPet

    private var _liveDataEvent: LiveData<RandomEventModel>? = null
    val liveDataEvent: LiveData<RandomEventModel>? get() = _liveDataEvent


    fun insertData(application: Application, name: String,
        age: String, hunger: String, health: String, energy: String) {
        MyRepository.insertData(application, name, age, hunger,health,energy)
    }

   /* fun getPetDetails(context: Application, id: Int) : LiveData<PetModel>? {
        _liveDataPet = MyRepository.getPetDetails(context,id)
        //liveDataPet = _liveDataPet
        return _liveDataPet
    }*/
    fun getPetDetails(application: Application, id: Int) {
       _liveDataPet = MyRepository.getPetDetails(application,id)
       //liveDataPet = _liveDataPet
    }

    fun deletePet(application: Application, id: Int) {
        MyRepository.deletePet(application, id)
    }

    fun getId(application: Application, name: String){
        _liveDataPet = MyRepository.getId(application,name)
    }

    fun getEvent(application: Application) {
        _liveDataEvent = MyRepository.getEvent(application)
        //return liveDataEvent
    }

    fun insertEvent(application: Application, event: String,
                   type: String, ev_hunger: String, ev_health: String, ev_energy: String) {
        MyRepository.insertData(application, event, type, ev_hunger,ev_health,ev_energy)
    }

}

