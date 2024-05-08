package com.example.vpet_tam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getPetDetails(application: Application, id: Int) {
       _liveDataPet = MyRepository.getPetDetails(application,id)
       //liveDataPet = _liveDataPet
    }

    fun deletePet(application: Application, id: Int) {
        MyRepository.deletePet(application, id)
    }

    fun getId(application: Application, name: String){
        _liveDataPet = MyRepository.getId(application,name)
        //_idState = MyRepository.getId(application,name)
    }

    fun getEvent(application: Application) {
        _liveDataEvent = MyRepository.getEvent(application)
        //return liveDataEvent
    }

    fun insertEvent(application: Application, event: String,
                   type: String, stat_type: String, x_num: Double, ev_time: Int) {

        MyRepository.insertEvent(application, event, type, stat_type, x_num, ev_time)
    }

    fun updatePetName(application: Application, id: Int, name: String){
        MyRepository.updatePetName(application,id,name)
    }

    fun updatePetStat(application: Application, id: Int, hunger: String, health: String, energy: String) {
        MyRepository.updatePetStat(application, id, hunger, health, energy)
    }

}

