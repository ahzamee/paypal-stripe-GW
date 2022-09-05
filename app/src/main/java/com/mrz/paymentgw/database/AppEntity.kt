package com.mrz.paymentgw.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "package_list")
class AppEntityPackageList(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "title")val title: String?,
    @ColumnInfo(name = "details")val details:String?,
    @ColumnInfo(name = "price")val price:Int?,
    @ColumnInfo(name = "status")val status:Boolean?,
    @ColumnInfo(name = "validity")val validity:Int?, //validity will be in days
    @ColumnInfo(name = "createdOn")val createdOn:String?
)

@Entity(tableName = "user_package_order")
class AppEntityUserPackageOrder(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "packageId")val packageId: Int?,
    @ColumnInfo(name = "purchased_on")val createdOn:String?
)