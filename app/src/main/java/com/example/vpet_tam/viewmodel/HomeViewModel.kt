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
    fun onButtonClicked(item: LinearLayout?) {
        if (item!!.visibility == View.VISIBLE) {
            item!!.visibility = View.INVISIBLE
        } else {
            item!!.visibility = View.VISIBLE
        }
    }



    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}