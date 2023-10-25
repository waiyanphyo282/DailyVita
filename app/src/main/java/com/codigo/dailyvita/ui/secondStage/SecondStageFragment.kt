package com.codigo.dailyvita.ui.secondStage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.codigo.dailyvita.R
import com.codigo.dailyvita.databinding.FragmentSecondStageBinding
import com.codigo.dailyvita.ui.DailyVitaViewModel
import com.codigo.dailyvita.ui.adapter.DietAdapter
import com.codigo.dailyvita.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondStageFragment : Fragment(R.layout.fragment_second_stage) {

    private lateinit var binding: FragmentSecondStageBinding
    private val viewModel by activityViewModels<DailyVitaViewModel>()
    private lateinit var dietAdapter: DietAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSecondStageBinding.bind(view)

        dietAdapter = DietAdapter()
        binding.rvDiets.adapter = dietAdapter

        val diets = viewModel.getDiets(requireContext())
        Log.d(TAG, "onViewCreated: Diets $diets")
        diets.let {
            dietAdapter.submitList(it)
        }

        dietAdapter.setOnCheckListener {
//            dietAdapter.currentList.find { it }
            viewModel.toggleCheckedDiets(it)
        }

        with(binding) {
            btnNext.setOnClickListener {
                if (viewModel.checkedDiets?.isNotEmpty() == true) {
                    findNavController().navigate(SecondStageFragmentDirections.actionSecondStageFragmentToThirdStageFragment())
                } else {
                    Toast.makeText(requireContext(), "Please Choose at least diet", Toast.LENGTH_SHORT).show()
                }
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}