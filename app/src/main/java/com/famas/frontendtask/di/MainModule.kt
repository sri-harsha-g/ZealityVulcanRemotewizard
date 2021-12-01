package com.famas.frontendtask.di

import android.app.Application
import androidx.compose.runtime.State
import androidx.room.Room
import com.famas.frontendtask.core.data.local.database.location_db.LocationDao
import com.famas.frontendtask.core.data.local.database.location_db.LocationDatabase
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.famas.frontendtask.core.data.repository.ThemingRepositoryImpl
import com.famas.frontendtask.core.domain.repository.ThemingRepository
import com.famas.frontendtask.core.util.Constants
import com.famas.frontendtask.core.util.network_status.ConnectionState
import com.famas.frontendtask.core.util.network_status.observeConnectivityAsFlow
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideDatastore(application: Application): Datastore {
        return Datastore(application)
    }

    @Provides
    @Singleton
    fun provideLocationDatabase(
        application: Application
    ): LocationDatabase {
        return Room.databaseBuilder(
            application,
            LocationDatabase::class.java,
            Constants.LOCATION_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationDao(locationDatabase: LocationDatabase) : LocationDao = locationDatabase.locationDao()

    @Provides
    @Singleton
    fun provideThemingRepo(datastore: Datastore): ThemingRepository {
        return ThemingRepositoryImpl(datastore)
    }
}