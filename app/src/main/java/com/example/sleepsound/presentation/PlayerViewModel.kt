package com.example.sleepsound.presentation

import androidx.lifecycle.ViewModel
import com.example.sleepsound.R
import com.example.sleepsound.core.player.PlayerManager
import com.example.sleepsound.domain.model.Sound
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val player: PlayerManager,
) : ViewModel() {

    private val _state = MutableStateFlow<List<Sound>>(emptyList())
    val state = _state

    init {
        loadSounds()
    }

    private fun loadSounds() {
        _state.value = listOf(
            Sound(1, "Rain", R.drawable.rain, R.raw.rain),
            Sound(2, "Grasshopper", R.drawable.grasshopper, R.raw.grasshopper),
            Sound(3, "River", R.drawable.river, R.raw.river),
            Sound(4, "Ocean Waves", R.drawable.oceanwaves, R.raw.oceanwaves),
            Sound(5, "Fire", R.drawable.fire, R.raw.firesound),
            Sound(6, "Birds", R.drawable.bird, R.raw.bird),
            Sound(7, "Cold Wind", R.drawable.coldwind, R.raw.coldwind),
            Sound(8, "Thunder", R.drawable.thunder, R.raw.thunder),
            Sound(9, "White Noise", R.drawable.whitenoise, R.raw.whitenoise),
            Sound(10, "Snow Walk", R.drawable.snow, R.raw.snow),
        )
    }

    fun togglePlay(sound: Sound) {
        player.togglePlay(sound.audioRes)
    }

    fun isPlaying(sound: Sound): Boolean {
        return player.isPlaying(sound.audioRes)
    }

    fun pause(sound: Sound) {
        player.pauseSound(sound.audioRes)
    }

    fun stopAllSounds() {
        player.stopAll()
    }

    override fun onCleared() {
        player.stopAll()
        super.onCleared()
    }
    fun setVolume(volume: Float) {
        player.setVolume(volume)
    }
}

