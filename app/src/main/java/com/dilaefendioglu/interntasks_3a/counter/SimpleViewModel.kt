package com.dilaefendioglu.interntasks_3a.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimpleViewModel : ViewModel() {
    // 1. MutableLiveData nesnesi, sayaç değerini tutar.
    // 2. LiveData nesnesi, sayaç değerini dısarıdan okunmasını sağlar
    private val _counter = MutableLiveData<Int>().apply { value = 0 }
    val counter: LiveData<Int> get() = _counter

    fun incrementButton() {
        //sayacı bir artırır.yeni sayac değerini countera atar.
        _counter.value = (_counter.value ?: 0) + 1
    }

    fun resetCounter() {
        _counter.value = 0
    }
}
