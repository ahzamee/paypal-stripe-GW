package com.mrz.paymentgw.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrz.paymentgw.database.AppEntityPackageList
import com.mrz.paymentgw.database.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PackageListViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {

    var userData: MutableLiveData<List<AppEntityPackageList>>

    init {
        userData = MutableLiveData()
        loadRecords()
    }

    fun getRecordsObserver(): MutableLiveData<List<AppEntityPackageList>> {
        return userData
    }

    fun loadRecords(){
        val list = repository.getAllRecords()
        userData.postValue(list)
    }
}