package com.dilaefendioglu.interntasks_3a.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GuessNumberViewModel : ViewModel() {
    private val _targetNumber = MutableLiveData<Int>()
    val targetNumber: LiveData<Int> get() = _targetNumber

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> get() = _statusMessage

    private val _randomChar = MutableLiveData<Char>()
    val randomChar: LiveData<Char> get() = _randomChar

    init {
        startGame()
    }

    fun startGame() {
        _targetNumber.value = (0..9).random()
        _randomChar.value = ('A'..'Z').random() // Rastgele karakter üretme
        _statusMessage.value = "Tahmin et!"
    }

    fun makeGuess(guess: Int) {
        if (guess == _targetNumber.value) {
            _statusMessage.value = "Kazandın! Sayı: $guess"
        } else {
            _statusMessage.value = "Tekrar dene!"
        }
    }
}