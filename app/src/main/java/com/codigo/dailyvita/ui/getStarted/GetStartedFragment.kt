package com.codigo.dailyvita.ui.getStarted

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codigo.dailyvita.R
import com.codigo.dailyvita.databinding.FragmentGetStartedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetStartedFragment : Fragment(R.layout.fragment_get_started) {

    private lateinit var binding: FragmentGetStartedBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGetStartedBinding.bind(view)

        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(GetStartedFragmentDirections.actionGetStartedFragmentToFirstStageFragment())
        }
    }

}