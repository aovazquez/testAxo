package com.mx.testcore.di

import android.content.Context
import com.mx.testcore.data.room.AppDao
import com.mx.testcore.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDatabase = AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): AppDao = database.appDao()

}