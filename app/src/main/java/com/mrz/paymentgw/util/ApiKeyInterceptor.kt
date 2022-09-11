package com.mrz.paymentgw.util

import com.mrz.paymentgw.BuildConfig._AUTH
import okhttp3.Interceptor
import okhttp3.Response



class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request().newBuilder().addHeader("Authorization", _AUTH)
        val newRequest = currentRequest.url(chain.request().url).build()
        return chain.proceed(newRequest)
    }
}
