package com.codigo.dailyvita.ui.firstStage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.RecyclerView
import com.codigo.dailyvita.R
import com.codigo.dailyvita.databinding.FragmentFirstStageBinding
import com.codigo.dailyvita.ui.DailyVitaViewModel
import com.codigo.dailyvita.ui.adapter.ConcernAdapter
import com.codigo.dailyvita.utils.TAG
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstStageFragment : Fragment(R.layout.fragment_first_stage) {

    private lateinit var binding: FragmentFirstStageBinding
    private val viewModel by activityViewModels<DailyVitaViewModel>()
    private lateinit var concernAdapter: ConcernAdapter

    private val itemTouchHelper by lazy {
        val itemTouchCallback = object: ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val recyclerviewAdapter = recyclerView.adapter as ConcernAdapter
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                viewModel.swapHealthConcerns(fromPosition, toPosition)
                recyclerviewAdapter.moveItem(fromPosition, toPosition)
                recyclerviewAdapter.notifyItemMoved(fromPosition,toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

        }
        ItemTouchHelper(itemTouchCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFirstStageBinding.bind(view)

        val concerns = viewModel.getHealthConcerns(requireContext())
        Log.d(TAG, "onViewCreated: Concerns $concerns")

        itemTouchHelper.attachToRecyclerView(binding.rvConcerns)
        concernAdapter = ConcernAdapter()
        binding.rvConcerns.adapter = concernAdapter

        concerns.let {
            it.forEach {
                val chip = LayoutInflater.from(requireContext())
                .inflate(R.layout.dailyvita_chip, binding.cgConcerns, false) as Chip
                with(chip) {
                    id = it.id
                    text = it.name
                    isChecked = it.checked
                    setOnCheckedChangeListener { button, isChecked ->
                        Log.d(TAG, "onViewCreated: check change ${button.text} $isChecked")
                        viewModel.toggleCheckedConcerns(it)
                    }
                }
                binding.cgConcerns.addView(chip)
            }
        }

        viewModel.chosenHealthConcernsLiveData.observe(viewLifecycleOwner) { concerns->
            concernAdapter.submitList(concerns)
        }

        with(binding) {
            btnNext.setOnClickListener {
                Log.d(TAG, "onViewCreated: Current List ${concernAdapter.currentList}")
                if (concernAdapter.currentList.isEmpty()) {
                    Toast.makeText(requireContext(), "Please Choose at least one item", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(FirstStageFragmentDirections.actionFirstStageFragmentToSecondStageFragment())
                }
            }
            btnBack.setOnClickListener { findNavController().popBackStack() }
        }

    }

}