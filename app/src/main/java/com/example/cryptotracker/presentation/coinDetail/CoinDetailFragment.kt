package com.example.cryptotracker.presentation.coinDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.cryptotracker.databinding.FragmentCoinDetailBinding
import com.example.cryptotracker.presentation.base.BaseFragment
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import com.example.cryptotracker.presentation.util.snackbar
import com.github.mikephil.charting.data.LineData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>() {

    private val viewModel by viewModels<CoinDetailViewModel>()
    private val args: CoinDetailFragmentArgs by navArgs()
    private lateinit var coin: DetailedCoinViewState

    @Inject
    lateinit var chartStyle : DetailChartStyle

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentCoinDetailBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coin = args.coin
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupListeners()
        setupObservers()


        viewModel.onStart(coin.id)


    }

    private fun initViews() {
        binding.apply {
            tvCoinName.text = coin.name
            tvCoinPrice.text = "$" + coin.price
            tv24hChangePercent.apply {
                text = coin.changePercent
                setTextColor(ContextCompat.getColor(requireContext(), coin.color))
                setCompoundDrawablesWithIntrinsicBounds(coin.drawable, 0, 0, 0)
            }
            tvHigh24hValue.text = "$" + coin.highestPrice24h
            tvLow24hValue.text = "$" + coin.lowestPrice24h
            tvMarketCapValue.text ="$" + coin.marketCap
            tvSupplyValue.text = "$" + coin.supply
            tvVolumeValue.text = "$" + coin.volume
        }
    }


    private fun setupListeners() {
        binding.apply {
            chipOneDay.setOnClickListener { viewModel.onOneDayChipClicked() }
            chipSevenDay.setOnClickListener { viewModel.onSevenDayChipClicked() }
            chipOneMonth.setOnClickListener { viewModel.onOneMonthChipClicked() }
            chipThreeMonth.setOnClickListener { viewModel.onThreeMonthChipClicked() }
            chipOneYear.setOnClickListener { viewModel.onOneYearChipClicked() }
        }
    }

    private fun setupObservers() {
        viewModel.pricePair.observe(viewLifecycleOwner) { minPrice ->
            chartStyle.styleChart(binding.chartCoin, minPrice)
        }
        viewModel.lineDataSet.observe(viewLifecycleOwner) { lineDataSet ->
            chartStyle.styleLineDataSet(lineDataSet, coin.isPriceIncreasing)
            binding.chartCoin.apply {
                data = LineData(lineDataSet)
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

}