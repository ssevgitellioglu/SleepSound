package com.example.sleepsound.core.player

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val players = mutableMapOf<Int, MediaPlayer>() // resId -> MediaPlayer

    fun togglePlay(soundResId: Int) {
        val player = players[soundResId]
        if (player != null && player.isPlaying) {
            player.stop()
            player.release()
            players.remove(soundResId)
        } else {
            val newPlayer = MediaPlayer.create(context, soundResId).apply {
                isLooping = true
                start()
            }
            players[soundResId] = newPlayer
        }
    }

    fun stopAll() {
        players.values.forEach {
            if (it.isPlaying) it.stop()
            it.release()
        }
        players.clear()
    }

    fun pauseSound(soundResId: Int) {
        players[soundResId]?.pause()
    }


    fun isPlaying(soundResId: Int): Boolean {
        return players[soundResId]?.isPlaying == true
    }

    fun setVolume(volume: Float) {
        players.values.forEach {
            it.setVolume(volume, volume)
        }
    }

}
