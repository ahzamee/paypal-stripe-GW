package com.mrz.paymentgw.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrz.paymentgw.model.StripeAccessToken
import com.mrz.paymentgw.repository.StripeRepo
import com.mrz.paymentgw.util.Events
import com.mrz.paymentgw.util.Status
import com.mrz.paymentgw.util.Result
import com.paypal.checkout.order.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackageDetailsViewModel @Inject constructor(
    private val repo: StripeRepo
    ): ViewModel() {


    private val _stripeAccessToken = MutableLiveData<Events<Result<StripeAccessToken>>>()
    val stripeAccessToken: LiveData<Events<Result<StripeAccessToken>>> = _stripeAccessToken

    fun getStripeAccessToken(id: Int) = viewModelScope.launch{
        _stripeAccessToken.postValue(Events(Result(Status.LOADING, null, null)))
        _stripeAccessToken.postValue(Events(repo.getStripeAccessToken(id)))
    }

}