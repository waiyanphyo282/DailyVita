package com.codigo.dailyvita.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codigo.dailyvita.data.Diet
import com.codigo.dailyvita.databinding.ItemDietBinding

class DietAdapter: ListAdapter<Diet, DietAdapter.DietViewHolder>(DietDiff) {

    private var onCheckListener: ((Diet) -> Unit)? = null

    fun setOnCheckListener(listener: (Diet) -> Unit) {
        onCheckListener = listener
    }

    object DietDiff: DiffUtil.ItemCallback<Diet>() {
        override fun areItemsTheSame(oldItem: Diet, newItem: Diet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Diet, newItem: Diet): Boolean {
            return newItem == oldItem
        }

    }

    inner class DietViewHolder(private val binding: ItemDietBinding): ViewHolder(binding.root) {
        fun bindData(diet: Diet) {
            with(binding) {
                checkboxDiet.isChecked = diet.checked
                checkboxDiet.setOnCheckedChangeListener { _, _ ->
                    onCheckListener?.invoke(diet)
                }
                tvName.text = diet.name
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ivInfo.tooltipText = diet.tool_tip
                }
                ivInfo.setOnClickListener {
                    ivInfo.performLongClick()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        val binding = ItemDietBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DietViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}