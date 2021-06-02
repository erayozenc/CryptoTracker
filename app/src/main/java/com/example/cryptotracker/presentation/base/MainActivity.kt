package com.example.cryptotracker.presentation.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.ActivityMainBinding
import com.example.cryptotracker.presentation.base.MainActivityViewModel
import com.example.cryptotracker.presentation.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        viewModel.networkConnection.observe(this, {
            if (!it) {
                applicationContext.toast("No Connection")
            }
        })

        viewModel.bottomNavigationVisibility.observe(this, { navVisibility ->
            binding.bottomNav.visibility = navVisibility
        })

        binding.bottomNav.setupWithNavController(fragment.findNavController())

        fragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.coinDetailFragment -> {
                    viewModel.hideBottomNav()
                }
                R.id.selectCoinFragment -> {
                    viewModel.hideTopBar()
                    binding.topAppBar.inflateMenu(R.menu.top_bar_menu)
                }
                else -> {
                    viewModel.showBottomNav()
                }
            }
        }

    }

}