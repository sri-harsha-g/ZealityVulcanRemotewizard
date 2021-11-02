package com.famas.frontendtask.di

import android.app.Application
import com.famas.frontendtask.core.data.local.datastore.Datastore
import com.google.android.gms.location.LocationRequest
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}