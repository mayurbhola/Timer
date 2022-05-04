package com.mayurbhola.timer.di

import com.mayurbhola.timer.data.local.TimerDatabase
import com.mayurbhola.timer.data.repository.RepositoryImpl
import com.mayurbhola.timer.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDatabaseRepository(
        database: TimerDatabase
    ): Repository = RepositoryImpl(database)
}