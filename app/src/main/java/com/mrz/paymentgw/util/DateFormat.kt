package com.mrz.paymentgw.util

import android.os.Build
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

public fun todayDate(pattern :String): String {
    val sdf = SimpleDateFormat(pattern)
    return sdf.format(Date())
}

public fun convertDateToFormat(
    dateToConvert: String,
    inputDateFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    outputDateFormat: String =  "d'th' MMMM, yyyy HH:mm"
): String? {
    return try {
        //val zonedTime = ZonedDateTime.parse("2021-05-16T05:02:26")

        // First parse your string date in order to get Date object
        val dateObject = SimpleDateFormat(inputDateFormat, Locale.US).parse(dateToConvert)
        // Next format Date object to String
        dateObject?.run { SimpleDateFormat(outputDateFormat, Locale.US).format(this) }
    } catch (e: ParseException) {
        null // Most likely that 'inputDateFormat' or|and 'outputDateFormat' format doesn't fit 'dateToConvert' you trying to convert
    }
}