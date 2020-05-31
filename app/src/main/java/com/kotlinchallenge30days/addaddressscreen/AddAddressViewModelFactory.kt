package com.kotlinchallenge30days.addaddressscreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlinchallenge30days.database.AddressDatabaseDao

class AddAddressViewModelFactory(
    private val addressDatabaseDao: AddressDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAddressViewModel::class.java)) {
            return AddAddressViewModel(addressDatabaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
