package com.example.sleepsound.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleepsound.core.player.PlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val playerManager: PlayerManager
) : ViewModel() {

    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft: StateFlow<Long> = _timeLeft

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    fun startTimer(duration: Long) {
        if (_isRunning.value) return

        _isRunning.value = true
        _timeLeft.value = duration

        viewModelScope.launch {
            while (_timeLeft.value > 0L) {
                delay(1000)
                _timeLeft.value = (_timeLeft.value - 1000L).coerceAtLeast(0L)
            }

            _isRunning.value = false
            fadeOutAndStop()
        }
    }


    private suspend fun fadeOutAndStop() {
        Log.d("TIMER", "Bitti!")
        var volume = 1f

        while (volume > 0f) {
            volume -= 0.05f
            playerManager.setVolume(volume.coerceAtLeast(0f))
            delay(150)
        }

        playerManager.stopAll()
        _isRunning.value = false
    }

    fun stopTimer() {
        _isRunning.value = false
        _timeLeft.value = 0
        playerManager.stopAll()
    }
}