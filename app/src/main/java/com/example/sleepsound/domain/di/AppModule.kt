package com.example.sleepsound.domain.di

import android.content.Context
import com.example.sleepsound.core.player.PlayerManager
import com.example.sleepsound.data.SoundRepositoryImpl
import com.example.sleepsound.domain.repository.SoundRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSoundRepository(): SoundRepository {
        return SoundRepositoryImpl()
    }
    @Provides
    @Singleton
    fun providePlayerManager(
        @ApplicationContext context: Context
    ): PlayerManager {
        return PlayerManager(context)
    }
}