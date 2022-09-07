package com.mrz.paymentgw.di

import android.app.Application
import com.mrz.paymentgw.database.AppDao
import com.mrz.paymentgw.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getAppDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Singleton
    @Provides
    fun appDao(appDB: AppDatabase): AppDao {
        return appDB.getAppDao()
    }
}
