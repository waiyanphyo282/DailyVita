package com.codigo.dailyvita

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.codigo.dailyvita.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.getStartedFragment -> {
                    binding.vitaminProgress.visibility = View.GONE
                }
                R.id.firstStageFragment -> {
                    with(binding) {
                        vitaminProgress.visibility = View.VISIBLE
                        vitaminProgress.progress = 25
                    }
                }
                R.id.secondStageFragment -> {
                    with(binding) {
                        vitaminProgress.visibility = View.VISIBLE
                        vitaminProgress.progress = 50
                    }
                }
                R.id.thirdStageFragment -> {
                    with(binding) {
                        vitaminProgress.visibility = View.VISIBLE
                        vitaminProgress.progress = 75
                    }
                }
                R.id.finalStageFragment -> {
                    with(binding) {
                        vitaminProgress.visibility = View.VISIBLE
                        vitaminProgress.progress = 100
                    }
                }
            }
        }
    }
}