package com.example.sleepsound.domain.repository

import com.example.sleepsound.domain.model.Sound

interface SoundRepository {
    fun getSounds(): List<Sound>
}
