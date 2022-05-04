package com.mayurbhola.timer.di

import android.content.Context
import com.mayurbhola.timer.data.local.TimerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TimerDatabase {
        return TimerDatabase.buildDatabase(context)
    }
}