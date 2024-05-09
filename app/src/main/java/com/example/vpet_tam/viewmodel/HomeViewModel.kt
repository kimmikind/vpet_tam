package com.example.vpet_tam.viewmodel

import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vpet_tam.R

class HomeViewModel : ViewModel() {
    private val _idState = MutableLiveData<Int>()
    val idState: MutableLiveData<Int> get() = _idState

    private val _imgState = MutableLiveData<Int>()
    val imgState: MutableLiveData<Int> get() = _imgState

    private val _eventState = MutableLiveData<String>()

    val eventState: MutableLiveData<String> get() = _eventState

    private val _xnumber = MutableLiveData<Double>()
    private val _evtime = MutableLiveData<Int>()
    private val _stat = MutableLiveData<String>()
    private val _type = MutableLiveData<String>()

    val xnumber: MutableLiveData<Double> get() = _xnumber
    val evtime: MutableLiveData<Int> get() = _evtime
    val stat: MutableLiveData<String> get() = _stat
    val type: MutableLiveData<String> get() = _type

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
        //Health indicators decrease 2X for the next minute
        val a_stat = arrayOf("Health","Hunger","Energy","All").random()
        val a_type = arrayOf("decrease","increase", "stopped").random()
        val a_number = doubleArrayOf(1.5,2.0,3.0,1.25,1.75).random()
        val a_time = arrayOf(0,30,20,15,40).random()
        var result = a_stat + " indicator " + a_type + " "+
                    a_number +"X for the next " + a_time + " seconds"

        _eventState.value = result
        _stat.value = a_stat
        when(a_type) {
            "decrease" -> _type.value = "-"
            "increase" -> _type.value = "+"
            else -> _type.value = "0"
        }
        _xnumber.value = a_number
        _evtime.value = a_time

    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}