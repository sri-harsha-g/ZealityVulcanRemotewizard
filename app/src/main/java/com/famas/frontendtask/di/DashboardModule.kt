package com.famas.frontendtask.di

import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.feature_dash_board.data.remote.DashboardApi
import com.famas.frontendtask.feature_dash_board.data.repository.DashboardRepositoryImpl
import com.famas.frontendtask.feature_dash_board.domain.presentation.DashboardRepository
import com.famas.frontendtask.feature_dash_board.domain.use_cases.GetDashboardUseCase
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
object DashboardModule {


    @Provides
    @Singleton
    fun provideDashboardApi(gson: Gson): DashboardApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(DashboardApi::class.java)

    @Provides
    @Singleton
    fun provideDashboardRepository(dashboardApi: DashboardApi): DashboardRepository {
        return DashboardRepositoryImpl(dashboardApi)
    }

    @Provides
    @Singleton
    fun provideGetDashboardUseCase(dashboardRepository: DashboardRepository): GetDashboardUseCase =
        GetDashboardUseCase(dashboardRepository)
}