package com.mrz.paymentgw.model

data class StripeAccessToken(
    val clientSecret: String,
    val message: String
)