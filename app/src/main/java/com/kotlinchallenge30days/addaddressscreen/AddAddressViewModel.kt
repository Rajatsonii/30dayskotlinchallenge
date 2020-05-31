package com.kotlinchallenge30days.addaddressscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlinchallenge30days.database.Address
import com.kotlinchallenge30days.database.AddressDatabaseDao
import kotlinx.coroutines.*

class AddAddressViewModel(
    private val addressDatabaseDao: AddressDatabaseDao,
    application: Application
) : AndroidViewModel(application) {


    var viewModelJob = Job()
    var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _addressInsertDone = MutableLiveData<Boolean>()

    private var _isLoading = MutableLiveData<Boolean>()

    val addressInsertDone: LiveData<Boolean>
        get() = _addressInsertDone

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun addAddress(
        name: String,
        phone: String,
        email: String,
        society: String,
        landmark: String,
        city: String,
        pincode: String,
        state: String,
        area: String
    ) {
        displayLoading(true)
        uiScope.launch {
            val address = Address(
                name = name,
                phone = phone,
                email = email,
                society = society,
                landmark = landmark,
                area = area,
                city = city,
                state = state,
                pincode = pincode
            )
            insert(address)
            displayLoading(false)
            _addressInsertDone.value = true
        }
    }

    private suspend fun insert(address: Address) {
        withContext(Dispatchers.IO) {
            addressDatabaseDao.insert(address)
        }
    }

    fun displayLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}





