package com.example.sleepsound.domain.usecase

import com.example.sleepsound.domain.model.Sound
import com.example.sleepsound.domain.repository.SoundRepository
import javax.inject.Inject

class GetSoundsByCategoryUseCase @Inject constructor(
    private val repository: SoundRepository
) {
    operator fun invoke(): List<Sound> {
        return repository.getSounds()
    }
}