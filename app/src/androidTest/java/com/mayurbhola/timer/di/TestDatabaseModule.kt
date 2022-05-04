package com.mayurbhola.timer.di

import android.app.Application
import androidx.room.Room
import com.mayurbhola.timer.data.local.TimerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): TimerDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            TimerDatabase::class.java
        ).build()
    }
}