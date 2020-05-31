package com.kotlinchallenge30days.addressesscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinchallenge30days.R
import com.kotlinchallenge30days.database.AddressDatabase
import com.kotlinchallenge30days.database.AddressDatabaseDao
import com.kotlinchallenge30days.databinding.FragmentAddressListBinding
import kotlinx.android.synthetic.main.fragment_address_list.*

class AddressListFragment : Fragment() {
    private lateinit var addressAdapter: AddressAdapter
    lateinit var addressListViewModel: AddressesViewModel
    lateinit var addressDatabaseDao: AddressDatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddressListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_address_list, container, false)

        val application = requireNotNull(this.activity).application

        AddressDatabase.getInstance(application)?.let {
            addressDatabaseDao = it.addressDatabaseDao

            val viewModelFactory = AddressListViewModelFactory(addressDatabaseDao, application)

            addressListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(AddressesViewModel::class.java)

            binding.viewModel = addressListViewModel
        }
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setObservable()
    }

    private fun setRecyclerView() {
        rvAddress.layoutManager = LinearLayoutManager(requireActivity())
        addressAdapter =
            AddressAdapter(requireActivity()) { position, address, view ->
                when (view.id) {
                    R.id.tvRemove -> addressListViewModel.removeAddresses(address.id)
                }
            }

        rvAddress.adapter = addressAdapter
    }


    private fun setObservable() {
        if (::addressListViewModel.isInitialized) {
            addressListViewModel.address.observe(viewLifecycleOwner, Observer { addressList ->
                if (addressList != null && addressList.isNotEmpty()) {
                    addressAdapter.submitList(addressList)
                } else {
                    addressAdapter.submitList(addressList)
                    Toast.makeText(requireActivity(), "No Address Found", Toast.LENGTH_LONG).show()
                }
            })
            addressListViewModel.navigateToAddAddress.observe(
                viewLifecycleOwner,
                Observer { shouldObserve ->
                    if (shouldObserve) {
                        this.findNavController()
                            .navigate(R.id.action_addressListFragment_to_addAddressFragment)
                        addressListViewModel.doneNavigateToAddAddress()
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        addressListViewModel.getAllAddress()
    }
}






