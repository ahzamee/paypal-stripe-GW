package com.mrz.paymentgw.repository

import com.mrz.paymentgw.model.StripeAccessToken
import com.mrz.paymentgw.remote.PaymentGWInterface
import com.mrz.paymentgw.util.Status
import com.mrz.paymentgw.util.Result

class StripeRepo(private val paymentGWInterface: PaymentGWInterface){

    suspend fun getStripeAccessToken(id: Int): Result<StripeAccessToken> {

        return try {
            val response = paymentGWInterface.getStripeToken(id)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, data = response.body(), null)
            } else {
                Result(Status.ERROR, data = null, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, data = null, null)
        }
    }
}
