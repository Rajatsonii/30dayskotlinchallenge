package com.kotlinchallenge30days.addressesscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlinchallenge30days.database.Address
import com.kotlinchallenge30days.databinding.SingleAddressBinding

class AddressAdapter(
    internal var context: Context,
    private var addressListner: (Int, Address, View) -> Unit
) : ListAdapter<Address, AddressAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, addressListner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: SingleAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Address, addressListener: (Int, Address, View) -> Unit) {
            binding.address = item
            binding.executePendingBindings()
            binding.tvRemove.setOnClickListener { addressListener(adapterPosition, item, it) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleAddressBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class SleepNightDiffCallback : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
            return oldItem == newItem
        }
    }
}

