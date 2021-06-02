package com.example.cryptotracker.presentation.coinComparison

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentCoinComparisonBinding
import com.example.cryptotracker.presentation.base.BaseFragment
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import com.example.cryptotracker.presentation.util.getBackStackData
import com.example.cryptotracker.presentation.util.snackbar
import com.github.mikephil.charting.data.LineData
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
@FlowPreview
class CoinComparisonFragment : BaseFragment<FragmentCoinComparisonBinding>() {

    private val viewModel by viewModels<CoinComparisonViewModel>()

    @Inject
    lateinit var chartStyle : ComparisonChartStyle

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentCoinComparisonBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupEventListeners()
        setupListeners()

        chartStyle.styleChart(binding.chartComparison)

        binding.apply {
            tvFirstCoinName.text = "BTC"
            tvSecondCoinName.text = "ETH"

        }

    }

    override fun onResume() {
        super.onResume()

        getBackStackData<DetailedCoinViewState>("selectedCoin") { coin ->
            if (coin != null)
                viewModel.onAfterSelectCoin(coin)
        }
    }

    private fun setupObservers() {
        viewModel.selectedCoins.observe(viewLifecycleOwner) { selectedCoins ->
            Glide.with(binding.ivFirstCoinIcon).load(selectedCoins[0].image).into(binding.ivFirstCoinIcon)
            Glide.with(binding.ivSecondCoinIcon).load(selectedCoins[1].image).into(binding.ivSecondCoinIcon)
            binding.apply {
                tvFirstCoinName.text = selectedCoins[0].symbol
                tvSecondCoinName.text = selectedCoins[1].symbol
            }
        }

        viewModel.lineDataSets.observe(viewLifecycleOwner) { lineDataSets ->
            chartStyle.styleFirstLineDataSet(lineDataSets[0])
            chartStyle.styleSecondLineDataSet(lineDataSets[1])
            binding.chartComparison.apply {
                data = LineData(lineDataSets)
                invalidate()
            }
        }

        viewModel.progressBar.observe(viewLifecycleOwner) { isProgress ->
            showDialog(isProgress)
        }

        viewModel.snackbar.observe(viewLifecycleOwner) { message ->
            requireView().snackbar(message)
        }
    }

    private fun setupEventListeners() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.comparisonEvent.collect { event ->
                when(event) {
                    is CoinComparisonViewModel.ComparisonEvent.NavigateToChangeSelectedCoin -> {
                        navigateFragment(R.id.selectCoinFragment)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            chipOneDay.setOnClickListener { viewModel.onIntervalChipClicked(CoinComparisonViewModel.Interval.ONE_DAY) }
            chipSevenDay.setOnClickListener { viewModel.onIntervalChipClicked(CoinComparisonViewModel.Interval.SEVEN_DAY) }
            chipOneMonth.setOnClickListener { viewModel.onIntervalChipClicked(CoinComparisonViewModel.Interval.ONE_MONTH) }
            chipThreeMonth.setOnClickListener { viewModel.onIntervalChipClicked(CoinComparisonViewModel.Interval.THREE_MONTH) }
            chipOneYear.setOnClickListener { viewModel.onIntervalChipClicked(CoinComparisonViewModel.Interval.ONE_YEAR) }

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when(tab.position) {
                        0 -> viewModel.onDataTypeTabClicked(CoinComparisonViewModel.DataType.PRICE)
                        1 -> viewModel.onDataTypeTabClicked(CoinComparisonViewModel.DataType.MARKET_CAP)
                        2 -> viewModel.onDataTypeTabClicked(CoinComparisonViewModel.DataType.VOLUME)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}

            })

            ivFirstCoinIcon.setOnClickListener { viewModel.onFirstCoinSelected() }
            ivSecondCoinIcon.setOnClickListener { viewModel.onSecondCoinSelected() }
        }
    }

}