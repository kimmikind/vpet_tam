package com.example.vpet_tam.ui.choosenewpet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChooseViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is choose Fragment"
    }
    val text: LiveData<String> = _text
}