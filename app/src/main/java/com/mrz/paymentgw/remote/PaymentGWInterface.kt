package com.mrz.paymentgw.remote

import com.mrz.paymentgw.model.StripeAccessToken
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentGWInterface {

    @GET("stripe-payment-intent")
    suspend fun getStripeToken(
        @Query("plan_id") page: Int
    ): Response<StripeAccessToken>
}