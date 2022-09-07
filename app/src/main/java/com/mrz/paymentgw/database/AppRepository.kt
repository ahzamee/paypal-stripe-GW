package com.mrz.paymentgw.database

import kotlinx.coroutines.flow.Flow
import org.w3c.dom.CharacterData
import javax.inject.Inject

class AppRepository @Inject constructor(private val appDao: AppDao) {

    fun getAllRecords(): List<AppEntityPackageList> {
        return appDao.packageList()
    }
}