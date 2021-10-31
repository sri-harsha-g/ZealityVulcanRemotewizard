package com.famas.frontendtask.di

import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.feature_requests.data.remote.RequestsApi
import com.famas.frontendtask.feature_requests.data.repository.RequestsRepositoryImpl
import com.famas.frontendtask.feature_requests.domain.repository.RequestsRepository
import com.famas.frontendtask.feature_requests.domain.user_case.GetRequestsUseCase
import com.famas.frontendtask.feature_requests.domain.user_case.PlaceRequestUseCase
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
object RequestsModule {

    @Provides
    @Singleton
    fun provideRequestsApi(gson: Gson): RequestsApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(Constants.BASE_URL)
        .build()
        .create(RequestsApi::class.java)

    @Provides
    @Singleton
    fun provideRequestsRepository(requestsApi: RequestsApi): RequestsRepository {
        return RequestsRepositoryImpl(requestsApi)
    }

    @Provides
    @Singleton
    fun provideGetRequestsUseCase(requestsRepository: RequestsRepository): GetRequestsUseCase {
        return GetRequestsUseCase(requestsRepository)
    }

    @Provides
    @Singleton
    fun providePlaceRequestsUseCase(requestsRepository: RequestsRepository): PlaceRequestUseCase {
        return PlaceRequestUseCase(requestsRepository)
    }
}