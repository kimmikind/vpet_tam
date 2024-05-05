package com.example.vpet_tam.ui.choosenewpet

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vpet_tam.R

class ChooseViewModel : ViewModel() {
    private val _chooseState = MutableLiveData<Int>()
    val chooseState: MutableLiveData<Int> get() = _chooseState
    fun onButtonClicked() {
        TODO("Not yet implemented")

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


        /*.apply {
        value = "This is choose Fragment"
    }*/
    //val text: LiveData<String> = _text
}