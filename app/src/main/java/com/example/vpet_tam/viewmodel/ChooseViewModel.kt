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
        var img = 0
        when(item.id) {
            R.id.icon_bear -> img = R.drawable.img_art_bear
            R.id.icon_cat -> img = R.drawable.img_art_cat
            R.id.icon_rabbit -> img = R.drawable.img_art_bunny
            R.id.icon_dog -> img = R.drawable.img_art_dog
            R.id.icon_turtle -> img = R.drawable.img_art_turtle
            R.id.icon_frog -> img = R.drawable.img_art_frog
           else -> 0
       }
        _chooseState.value = img
    }

    fun inputName(name: String) {
        _nameState.value = name
    }

}