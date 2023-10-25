package com.codigo.dailyvita.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codigo.dailyvita.data.HealthConcern
import com.codigo.dailyvita.databinding.ItemHealthConcernBinding

class ConcernAdapter: ListAdapter<HealthConcern, ConcernAdapter.ConcernViewHolder>(ConcernDiff) {

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val list = currentList.toMutableList()
        val fromItem = list[fromPosition]
        list.removeAt(fromPosition)
        if (toPosition < fromPosition) {
            list.add(toPosition + 1 , fromItem)
        } else {
            list.add(toPosition - 1, fromItem)
        }
        submitList(list)
    }

    object ConcernDiff: DiffUtil.ItemCallback<HealthConcern>() {
        override fun areItemsTheSame(oldItem: HealthConcern, newItem: HealthConcern): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HealthConcern, newItem: HealthConcern): Boolean {
            return oldItem == newItem
        }

    }

    inner class ConcernViewHolder(val binding: ItemHealthConcernBinding): ViewHolder(binding.root) {
        fun bindData(concern: HealthConcern) {
            with(binding) {
                chipConcern.text = concern.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcernViewHolder {
        val binding = ItemHealthConcernBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcernViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConcernViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}