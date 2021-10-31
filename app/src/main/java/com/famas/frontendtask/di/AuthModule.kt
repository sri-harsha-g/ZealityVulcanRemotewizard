package com.famas.frontendtask.di

import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.feature_auth.data.remote.AuthApi
import com.famas.frontendtask.feature_auth.data.repository.AuthRepositoryImpl
import com.famas.frontendtask.feature_auth.domain.repository.AuthRepository
import com.famas.frontendtask.feature_auth.domain.use_cases.LoginUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(gson: Gson): AuthApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }
}