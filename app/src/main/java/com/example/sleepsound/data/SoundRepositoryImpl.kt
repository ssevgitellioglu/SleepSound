package com.example.sleepsound.data


import com.example.sleepsound.R
import com.example.sleepsound.domain.model.Sound
import com.example.sleepsound.domain.repository.SoundRepository
import javax.inject.Inject

class SoundRepositoryImpl @Inject constructor() : SoundRepository {
    override fun getSounds(): List<Sound> {
        return listOf(
            Sound(1, "Rain", R.drawable.rain, R.raw.rain),
            Sound(2, "Grasshopper", R.drawable.grasshopper, R.raw.grasshopper),
            Sound(3, "River", R.drawable.river, R.raw.river),
            Sound(4, "Ocean Waves", R.drawable.oceanwaves, R.raw.oceanwaves),
            Sound(5, "Fire", R.drawable.fire, R.raw.firesound),
            Sound(6, "Birds", R.drawable.bird, R.raw.bird),
            Sound(7, "Cold Wind", R.drawable.coldwind, R.raw.coldwind),
            Sound(8, "Thunder", R.drawable.thunder, R.raw.thunder),
            Sound(9, "White Noise", R.drawable.whitenoise, R.raw.whitenoise),
            Sound(10, "Snowy", R.drawable.snow, R.raw.snow),

            )
    }
}