package com.codigo.dailyvita.ui.thirdStage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.codigo.dailyvita.R
import com.codigo.dailyvita.databinding.FragmentThirdStageBinding
import com.codigo.dailyvita.ui.DailyVitaViewModel
import com.codigo.dailyvita.ui.adapter.AllergyAdapter
import com.codigo.dailyvita.utils.TAG
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdStageFragment : Fragment(R.layout.fragment_third_stage) {

    private lateinit var binding: FragmentThirdStageBinding
    private val viewModel by activityViewModels<DailyVitaViewModel>()
    private lateinit var allergyAdapter: AllergyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentThirdStageBinding.bind(view)

        allergyAdapter = AllergyAdapter()
        binding.rvAllergies.adapter = allergyAdapter

        val allergies = viewModel.getAllergies(requireContext())
        allergyAdapter.submitList(allergies)

        allergyAdapter.setOnItemClickListener {
            viewModel.toggleAllergies(it)
        }

        viewModel.allergiesLiveData.observe(viewLifecycleOwner) {
            binding.allergyChipGroup.removeAllViews()
            it.forEach {allergy ->
                val chip = LayoutInflater.from(requireContext())
                    .inflate(R.layout.dailyvita_chip, binding.allergyChipGroup, false) as Chip
                with(chip) {
                    isChecked = true
                    text = allergy.name
                    setOnClickListener {
                        viewModel.toggleAllergies(allergy)
                    }
                }
                binding.allergyChipGroup.addView(chip)}
        }

        with(binding) {

            allergyInput.requestFocus()

            allergyInput.doOnTextChanged { text, _, _, _ ->
                Log.d(TAG, "onViewCreated: filter Text $text")
                allergyAdapter.filter.filter(text)
            }

            btnNext.setOnClickListener {
                findNavController().navigate(ThirdStageFragmentDirections.actionThirdStageFragmentToFinalStageFragment())
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}