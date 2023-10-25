package com.codigo.dailyvita.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.codigo.dailyvita.data.Allergy
import com.codigo.dailyvita.databinding.ItemAllergyBinding

class AllergyAdapter: ListAdapter<Allergy, AllergyAdapter.AllergyViewHolder>(AllergyDiff),
    Filterable {

    private var onItemClickListener: ((Allergy) -> Unit)? = null

    private var originalList: List<Allergy> = currentList.toList()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (constraint.isNullOrEmpty())
                        originalList
                    else originalList.filter { it.name.lowercase().contains(constraint) }
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<Allergy>, true)
            }
        }
    }

    override fun submitList(list: List<Allergy>?) {
        submitList(list, false)
    }

    private fun submitList(list: List<Allergy>?, filtered: Boolean) {
        if (!filtered)
            originalList = list ?: listOf()

        super.submitList(list)
    }

    fun setOnItemClickListener(listener: (Allergy) -> Unit) {
        onItemClickListener = listener
    }

    object AllergyDiff: DiffUtil.ItemCallback<Allergy>() {
        override fun areItemsTheSame(oldItem: Allergy, newItem: Allergy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Allergy, newItem: Allergy): Boolean {
            return oldItem == newItem
        }

    }

    inner class AllergyViewHolder(private val binding: ItemAllergyBinding): ViewHolder(binding.root) {
        fun bindData(allergy: Allergy) {
            binding.root.setOnClickListener { onItemClickListener?.invoke(allergy) }
            binding.tvName.text = allergy.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllergyViewHolder {
        val binding = ItemAllergyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllergyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllergyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}