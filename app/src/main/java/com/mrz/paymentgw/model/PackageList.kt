package com.mrz.paymentgw.model

import java.io.Serializable

data class PackageList(
    val id: Int,
    val title: String?,
    val details:String?,
    val price:Int?,
    val status:Boolean?,
    val validity:Int?, //validity will be in days
    val createdOn:String?
)
