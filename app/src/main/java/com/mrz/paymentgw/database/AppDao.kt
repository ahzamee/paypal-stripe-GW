package com.mrz.paymentgw.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("select * from package_list")
    fun packageList(): Flow<List<AppEntityPackageList>>
}