package com.example.vpet_tam.viewmodel

import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.vpet_tam.R

class ChooseViewModel: ViewModel() {
    private val _chooseState = MutableLiveData<Int>()
    private val _nameState = MutableLiveData<String>()
    val chooseState: MutableLiveData<Int> get() = _chooseState
    val nameState : MutableLiveData<String> get() = _nameState
    fun onButtonClicked(name: EditText, item: View) {

    }

    fun onIconClicked(item: View) {
       val img = when(item.id) {
            R.id.icon_bear -> 1
            R.id.icon_cat -> 2
            R.id.icon_rabbit -> 3
            R.id.icon_dog -> 4
            R.id.icon_turtle -> 5
            R.id.icon_frog -> 6
           else -> 0
       }
        _chooseState.value = img
    }

    fun inputName(name: String) {
        _nameState.value = name
    }

}