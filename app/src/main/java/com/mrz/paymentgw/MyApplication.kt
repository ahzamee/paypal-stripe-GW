package com.mrz.paymentgw

import android.app.Application
import com.mrz.paymentgw.BuildConfig.PAYPAL_SANDBOX_CLIENT_ID
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val config = CheckoutConfig(
            application = this,
            clientId = PAYPAL_SANDBOX_CLIENT_ID,
            environment = Environment.SANDBOX,
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )

        PayPalCheckout.setConfig(config)
    }
}