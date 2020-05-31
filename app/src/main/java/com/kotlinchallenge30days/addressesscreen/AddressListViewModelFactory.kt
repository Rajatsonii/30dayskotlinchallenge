package com.kotlinchallenge30days.addressesscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlinchallenge30days.database.AddressDatabaseDao

class AddressListViewModelFactory(
    private val addressDatabaseDao: AddressDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddressesViewModel::class.java)) {
            return AddressesViewModel(addressDatabaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
