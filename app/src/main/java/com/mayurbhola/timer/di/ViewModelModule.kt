package com.mayurbhola.timer.di

import com.mayurbhola.timer.domain.Repository
import com.mayurbhola.timer.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    fun provideSetTimer(repository: Repository): SetTimer = SetTimerImpl(repository)

    @Provides
    fun provideAllTimers(repository: Repository): AllTimers = AllTimersImpl(repository)

    @Provides
    fun provideUpdateTimer(repository: Repository): UpdateTimer = UpdateTimerImpl(repository)

    @Provides
    fun provideValidateTime(): ValidateTime = ValidateTimeImpl()
}