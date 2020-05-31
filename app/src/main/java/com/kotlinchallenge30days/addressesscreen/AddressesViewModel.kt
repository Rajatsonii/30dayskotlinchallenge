package com.kotlinchallenge30days.addressesscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlinchallenge30days.database.Address
import com.kotlinchallenge30days.database.AddressDatabaseDao
import kotlinx.coroutines.*

class AddressesViewModel(
    private val addressDatabaseDao: AddressDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _address = MutableLiveData<List<Address>>()
    private val _navigateToAddAddress = MutableLiveData<Boolean>()

    private var _isLoading = MutableLiveData<Boolean>()

    val address: LiveData<List<Address>>
        get() = _address

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val navigateToAddAddress: LiveData<Boolean>
        get() = _navigateToAddAddress


    fun getAllAddress() {
        displayLoading(true)
        uiScope.launch {
            _address.value = fetchAllAddress()
            displayLoading(false)
        }
    }

    private suspend fun fetchAllAddress(): List<Address> {
        return withContext(Dispatchers.IO) {
            addressDatabaseDao.getAllAddress()
        }
    }

    private suspend fun removeAddressesFromDb(id: Long) {
        withContext(Dispatchers.IO) {
            addressDatabaseDao.removeAddress(id)
        }
    }

    fun removeAddresses(id: Long) {
        displayLoading(true)
        uiScope.launch {
            removeAddressesFromDb(id)
            _address.value = fetchAllAddress()
            displayLoading(false)
        }
    }

    fun navigateToAddAddressFragment() {
        _navigateToAddAddress.value = true
    }

    fun doneNavigateToAddAddress() {
        _navigateToAddAddress.value = false
    }

    private fun displayLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


