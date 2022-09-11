package com.mrz.paymentgw.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.mrz.paymentgw.R
import kotlin.concurrent.thread

public fun showDialog(activity: Activity, message: String) {
    val alertDialog: AlertDialog = activity.let {
        val builder = AlertDialog.Builder(it)
        builder.setTitle(activity.resources.getString(R.string.dialog_title_status))
        builder.setMessage(message)

        // Create the AlertDialog
        builder.create()
    }
    //show dialog
    alertDialog.show()

    thread {
        Thread.sleep (5000)
        alertDialog.dismiss()
    }
}