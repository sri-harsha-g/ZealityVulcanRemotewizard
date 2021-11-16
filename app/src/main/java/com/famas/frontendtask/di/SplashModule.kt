package com.famas.frontendtask.di

import com.famas.frontendtask.feature_auth.data.remote.SplashApi
import com.famas.frontendtask.feature_auth.data.repository.SplashRepositoryImpl
import com.famas.frontendtask.feature_auth.domain.repository.SplashRepository
import com.famas.frontendtask.feature_auth.domain.use_cases.CheckForUpdates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashModule {


    @Provides
    @Singleton
    fun provideSplashApi(): SplashApi {
        return object : SplashApi {}
    }

    @Provides
    @Singleton
    fun provideSplashRepo(splashApi: SplashApi): SplashRepository {
        return SplashRepositoryImpl(splashApi)
    }

    @Provides
    @Singleton
    fun provideCheckUpdateUseCase(splashRepository: SplashRepository) = CheckForUpdates(splashRepository)
}