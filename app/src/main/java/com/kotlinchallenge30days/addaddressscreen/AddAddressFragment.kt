package com.kotlinchallenge30days.addaddressscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.kotlinchallenge30days.R
import com.kotlinchallenge30days.database.AddressDatabase
import com.kotlinchallenge30days.database.AddressDatabaseDao
import com.kotlinchallenge30days.databinding.FragmentAddAddressBinding
import kotlinx.android.synthetic.main.fragment_add_address.*

class AddAddressFragment : Fragment() {

    private lateinit var mViewModel: AddAddressViewModel
    lateinit var addressDatabaseDao: AddressDatabaseDao
    lateinit var binding: FragmentAddAddressBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(this.activity).application
        AddressDatabase.getInstance(application)?.let {
            addressDatabaseDao = it.addressDatabaseDao
            val viewModelFactory = AddAddressViewModelFactory(addressDatabaseDao, application)

            mViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(AddAddressViewModel::class.java)

            binding.viewModel = mViewModel
        }

        binding.lifecycleOwner = this
        setObservable()
        setListener()
    }

    private fun setObservable() {
        mViewModel.addressInsertDone.observe(viewLifecycleOwner, Observer { insertDone ->
            if (insertDone) {
                Toast.makeText(
                    requireActivity(),
                    "Address Inserted Successfully",
                    Toast.LENGTH_LONG
                ).show()
                this.findNavController()
                    .navigate(R.id.action_addAddressFragment_to_addressListFragment)
            }
        })
    }


    private fun setListener() {
        btnSave.setOnClickListener {
            if (isValid()) {
                mViewModel.addAddress(
                    etName.text.toString(),
                    etContactNumber.text.toString(),
                    etEmailAddress.text.toString(),
                    etSociety.text.toString(),
                    etLandmark.text.toString(),
                    etCity.text.toString(),
                    etPincode.text.toString(),
                    etStateName.text.toString(),
                    etArea.text.toString()
                )
            }
        }
    }


    private fun isValid(): Boolean {
        if (etName.text.toString().isEmpty()) {
            showError(R.string.err_fullname)
            return false
        }
        if (etEmailAddress.text.toString().isEmpty()) {
            showError(R.string.err_email)
            return false
        }

        if (etContactNumber.text.toString().isEmpty()) {
            showError(R.string.err_enter_phone)
            return false
        }

        if (etSociety.text.toString().isEmpty()) {
            showError(R.string.err_society)
            return false
        }
        if (etLandmark.text.toString().isEmpty()) {
            showError(R.string.err_landmark)
            return false
        }
        if (etArea.text.toString().isEmpty()) {
            showError(R.string.err_area)
            return false
        }
        if (etPincode.text.toString().isEmpty()) {
            showError(R.string.err_pincode)
            return false
        }
        if (etCity.text.toString().isEmpty()) {
            showError(R.string.err_city)
            return false
        }
        if (etStateName.text.toString().isEmpty()) {
            showError(R.string.err_state)
            return false
        }
        return true
    }


    private fun showError(@StringRes err: Int) {
        Toast.makeText(requireActivity(), getString(err), Toast.LENGTH_LONG).show()
    }
}



