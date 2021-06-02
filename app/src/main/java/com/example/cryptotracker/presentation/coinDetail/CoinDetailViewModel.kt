package com.example.cryptotracker.presentation.coinDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.usecase.FetchCoinMarketChart
import com.example.cryptotracker.presentation.base.BaseViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val fetchCoinMarketChart: FetchCoinMarketChart
) : BaseViewModel() {

    companion object {
        private const val CHART_LABEL = "PRICE_CHART"
    }

    private var coinId = ""

    private val coinIdChannel = Channel<Pair<String, String>>()
    private val coinIdFlow = coinIdChannel.receiveAsFlow()

    private val _pricePair : MutableLiveData<Pair<Double, Double>> = MutableLiveData()
    val pricePair : LiveData<Pair<Double, Double>> = _pricePair

    val lineDataSet = coinIdFlow.flatMapLatest {
        _progressBar.value = true
        fetchCoinMarketChart.execute(it.first, it.second)
                .onStart { LineDataSet(listOf(), CHART_LABEL) }
                .mapLatest { resource ->
                    if (resource is Resource.Success) {
                        _pricePair.value =
                                Pair(
                                    resource.data.prices.minOf { price -> price.first },
                                    resource.data.prices.maxOf { price -> price.first }
                                )
                        var i = 1f
                        LineDataSet(
                            resource.data.prices.map { pair ->
                                Entry(i++, pair.first.toFloat())
                            },
                            CHART_LABEL
                        )
                    } else {
                        val error = resource as Resource.Error
                        throw Error(error.message)
                    }
                }
                .onCompletion { _progressBar.value = false }
                .catch { cause: Throwable ->
                    _snackbar.value = cause.message
                    println(cause.message)
                }
    }.asLiveData()

    fun onStart(coinId : String) = viewModelScope.launch {
        this@CoinDetailViewModel.coinId = coinId
        coinIdChannel.send(Pair(coinId, "30"))
    }

    fun onOneDayChipClicked() = viewModelScope.launch {
        _progressBar.value = true
        coinIdChannel.send(Pair(coinId, "1"))
    }

    fun onSevenDayChipClicked() = viewModelScope.launch {
        coinIdChannel.send(Pair(coinId, "7"))
    }

    fun onOneMonthChipClicked() = viewModelScope.launch {
        coinIdChannel.send(Pair(coinId, "30"))
    }

    fun onThreeMonthChipClicked() = viewModelScope.launch {
        coinIdChannel.send(Pair(coinId, "90"))
    }

    fun onOneYearChipClicked() = viewModelScope.launch {
        coinIdChannel.send(Pair(coinId, "365"))
    }

}