package com.example.cryptotracker.presentation.coinComparison

import androidx.lifecycle.*
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.usecase.FetchCoinMarketChart
import com.example.cryptotracker.domain.usecase.GetCoinFromDatabase
import com.example.cryptotracker.presentation.base.BaseViewModel
import com.example.cryptotracker.presentation.common.CoinViewState
import com.example.cryptotracker.presentation.coinList.CoinViewStateMapper
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
@FlowPreview
class CoinComparisonViewModel @Inject constructor(
    private val fetchCoinMarketChart: FetchCoinMarketChart,
    private val getCoinFromDatabase: GetCoinFromDatabase,
    private val mapper: CoinViewStateMapper
) : BaseViewModel() {

    companion object {
        private const val CHART_LABEL = "COMPARISON_CHART"
    }

    private var coinIds = Pair("bitcoin", "ethereum")
    private var days = "30"
    private var dataType = DataType.PRICE

    private var selectedIndex = 0

    private val comparisonEventChannel = Channel<ComparisonEvent>()
    val comparisonEvent = comparisonEventChannel.receiveAsFlow()

    private val coinIdsChannel = Channel<Triple<String, String, String>>()
    private val coinIdsFlow = coinIdsChannel.receiveAsFlow()

    private val selectCoinChannel = Channel<Pair<String, String>>()
    private val selectCoinFlow = selectCoinChannel.receiveAsFlow()

    val lineDataSets = coinIdsFlow.flatMapLatest {
        fetchLineDataSet(it.first, it.third, dataType)
                .zip(fetchLineDataSet(it.second, it.third, dataType)) {first, second ->
                    listOf(first, second)
                }
    }.asLiveData()

    val selectedCoins = selectCoinFlow.flatMapLatest {
        getCoin(it.first)
                .zip(getCoin(it.second)) {first, second ->
                    listOf(first, second)
                }
    }.asLiveData()

    private suspend fun fetchLineDataSet(coinId: String, days: String, dataType: DataType) =
            fetchCoinMarketChart.execute(coinId, days, "daily")
                    .onStart { LineDataSet(listOf(), CHART_LABEL) }
                    .mapLatest { resource ->
                        println("fetchLineDataSet")
                        if (resource is Resource.Success) {
                            println(resource.data)
                            var i = 1f
                            when(dataType) {
                                DataType.PRICE -> {
                                    LineDataSet(
                                            resource.data.prices.map { pair ->
                                                Entry(i++, pair.first.toFloat())
                                            },
                                            CHART_LABEL
                                    )
                                }
                                DataType.MARKET_CAP -> {
                                    LineDataSet(
                                            resource.data.marketCaps.map { pair ->
                                                Entry(i++, pair.first.toFloat())
                                            },
                                            CHART_LABEL
                                    )
                                }
                                DataType.VOLUME -> {
                                    LineDataSet(
                                            resource.data.volumes.map { pair ->
                                                Entry(i++, pair.first.toFloat())
                                            },
                                            CHART_LABEL
                                    )
                                }
                            }
                        } else {
                            val error = resource as Resource.Error
                            throw Error(error.message)
                        }
                    }
                    .catch { cause: Throwable ->
                        _snackbar.postValue(cause.message)
                        println(cause.message)
                    }


    private fun getCoin(id: String) =
            getCoinFromDatabase.execute(id)
                    .map { domainModel ->
                        println("getCoin")
                        mapper.map(domainModel)
                    }


    fun onStart() = viewModelScope.launch(Dispatchers.IO) {
        launch {
            coinIdsChannel.send(Triple("bitcoin", "ethereum", "30"))
        }
        launch {
            selectCoinChannel.send(Pair("bitcoin", "ethereum"))
        }
    }

    fun onFirstCoinSelected() = viewModelScope.launch {
        selectedIndex = 1
        comparisonEventChannel.send(ComparisonEvent.NavigateToChangeSelectedCoin(selectedIndex))
    }

    fun onSecondCoinSelected() = viewModelScope.launch {
        selectedIndex = 2
        comparisonEventChannel.send(ComparisonEvent.NavigateToChangeSelectedCoin(selectedIndex))
    }

    fun onAfterSelectCoin(coin: CoinViewState) = viewModelScope.launch {
        coinIds = if (selectedIndex == 1) Pair(coin.id, coinIds.second) else  Pair(coinIds.first, coin.id)
        coinIdsChannel.send(Triple(coinIds.first, coinIds.second, days))
        selectCoinChannel.send(Pair(coinIds.first, coinIds.second))
    }

    fun onIntervalChipClicked(interval: Interval) = viewModelScope.launch {
        coinIdsChannel.send(Triple(coinIds.first, coinIds.second, interval.toDay()))
    }

    fun onDataTypeTabClicked(dataType: DataType) = viewModelScope.launch {
        this@CoinComparisonViewModel.dataType = dataType
        coinIdsChannel.send(Triple(coinIds.first, coinIds.second, days))
    }

    sealed class ComparisonEvent {
        data class NavigateToChangeSelectedCoin(val no: Int): ComparisonEvent()
    }

    enum class DataType {
        PRICE, MARKET_CAP, VOLUME
    }

    enum class Interval {
        ONE_DAY, SEVEN_DAY, ONE_MONTH, THREE_MONTH, ONE_YEAR
    }

    private fun Interval.toDay() : String {
        return when(this) {
            Interval.ONE_DAY -> "1"
            Interval.SEVEN_DAY -> "7"
            Interval.ONE_MONTH -> "30"
            Interval.THREE_MONTH -> "90"
            Interval.ONE_YEAR -> "365"
        }
    }

}