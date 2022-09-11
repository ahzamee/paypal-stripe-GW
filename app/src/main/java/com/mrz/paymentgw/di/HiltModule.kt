package com.mrz.paymentgw.di

import android.app.Application
import com.mrz.paymentgw.BuildConfig.BASE_URL
import com.mrz.paymentgw.database.AppDao
import com.mrz.paymentgw.database.AppDatabase
import com.mrz.paymentgw.remote.PaymentGWInterface
import com.mrz.paymentgw.repository.StripeRepo
import com.mrz.paymentgw.util.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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

    @Singleton
    @Provides
    fun retrofitInterface(): PaymentGWInterface {

        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(PaymentGWInterface::class.java)
    }

    @Provides
    fun provideRepository(paymentGWInterface: PaymentGWInterface): StripeRepo {
        return StripeRepo(paymentGWInterface)
    }
}
