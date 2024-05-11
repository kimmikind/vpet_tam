package com.example.vpet_tam.viewmodel

import android.app.Application
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.vpet_tam.model.EventDetails
import com.example.vpet_tam.view.SettingsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _idState = MutableLiveData<Int>()
    val idState: MutableLiveData<Int> get() = _idState

    private val _imgState = MutableLiveData<Int>()
    val imgState: MutableLiveData<Int> get() = _imgState

    private val _eventDetails = MutableLiveData<EventDetails>()
    val eventDetails: MutableLiveData<EventDetails> get() = _eventDetails


    // отображение выпадающего из меню списка
    fun onButtonClicked(item: LinearLayout?) {
        if (item!!.visibility == View.VISIBLE) {
            item!!.visibility = View.INVISIBLE
        } else {
            item!!.visibility = View.VISIBLE
        }
    }

    fun onSaveId(id: Int) {
        _idState.value = id

    }

    fun onSaveImg(id: Int) {
        _imgState.value = id

    }
    fun onGenerateEvent() {
        var type : String =""
        //Health indicators decrease 2X for the next minute
        val a_stat = arrayOf("Health","Hunger","Energy","All").random()
        val a_type = arrayOf("decrease","increase", "stopped").random()
        val a_number = doubleArrayOf(1.5,2.0,3.0,1.25,1.75).random()

        val a_time = arrayOf(60,30,20,15,40).random()
        var result = a_stat + " indicator " + a_type + " "+
                    a_number +"X for the next " + a_time + " seconds"
        if (a_type == "stopped")  result = a_stat + " indicator " + a_type + " "+
               "for the next " + a_time + " seconds"
        when(a_type) {
            "decrease" -> type = "-"
            "increase" -> type = "+"
            else -> type = "0"
        }
        _eventDetails.value = EventDetails(result,a_number,a_time,a_stat,type)

    }


}