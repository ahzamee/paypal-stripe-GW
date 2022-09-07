package com.mrz.paymentgw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrz.paymentgw.Events
import com.mrz.paymentgw.database.AppEntityPackageList
import com.mrz.paymentgw.database.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackageListViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {

    lateinit var userData: MutableLiveData<List<AppEntityPackageList>>

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