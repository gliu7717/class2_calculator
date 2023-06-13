package com.example.mycalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel :ViewModel(){
    var progressValue = ""
    var result = MutableLiveData<Int>()

    init {
        result.value = 0
    }
    fun updateProgressValue(value:String)
    {
        progressValue = value
    }

    fun updateResult(value:Int)
    {
        result.value = value
    }

}