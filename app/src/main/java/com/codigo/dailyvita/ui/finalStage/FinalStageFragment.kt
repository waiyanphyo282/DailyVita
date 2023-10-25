package com.codigo.dailyvita.ui.finalStage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.codigo.dailyvita.R
import com.codigo.dailyvita.databinding.FragmentFinalStageBinding
import com.codigo.dailyvita.ui.DailyVitaViewModel
import com.codigo.dailyvita.utils.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinalStageFragment : Fragment(R.layout.fragment_final_stage) {

    private lateinit var binding: FragmentFinalStageBinding
    private val viewModel by activityViewModels<DailyVitaViewModel>()

    private var isSmoke: Boolean? = null
    private var isDailyExposure: Boolean? = null
    private var alcohol: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFinalStageBinding.bind(view)

        with(binding) {
            btnGetVitamin.setOnClickListener {
                when (sunExposureRadioGroup.checkedRadioButtonId) {
                    R.id.rb_sun_expo_yes -> { isDailyExposure = true }
                    R.id.rb_sun_expo_no -> { isDailyExposure = false }
                }
                when (smokeRadioGroup.checkedRadioButtonId) {
                    R.id.rb_smoke_yes -> {
                        isSmoke = true
                    }
                    R.id.rb_smoke_no -> {
                        isSmoke = false
                    }
                }
                when (alcoholRadioGroup.checkedRadioButtonId) {
                    R.id.rb_alcohol_0_1 -> {
                        alcohol = resources.getString(R.string._0_1).replace(" ", "")
                    }
                    R.id.rb_alcohol_2_5 -> {
                        alcohol = resources.getString(R.string._2_5).replace(" ", "")
                    }
                    R.id.rb_alcohol_5_plus -> {
                        alcohol = resources.getString(R.string._5).replace(" ", "")
                    }
                }

                Log.d(TAG, "onViewCreated: all values $isDailyExposure | $isSmoke | $alcohol")
                if (isDailyExposure == null || isSmoke == null || alcohol == null) {
                    Toast.makeText(requireContext(), "Please answer all the questions", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    viewModel.getVitamin(isSmoke!!, isDailyExposure!!, alcohol!!)
                }
            }
        }
    }

}