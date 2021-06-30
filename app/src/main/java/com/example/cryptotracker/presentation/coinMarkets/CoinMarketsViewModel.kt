package com.example.cryptotracker.presentation.coinMarkets

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.cryptotracker.domain.usecase.FetchCoinMarkets
import com.example.cryptotracker.presentation.base.BaseViewModel
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CoinMarketsViewModel @Inject constructor(
    private val fetchCoinMarkets: FetchCoinMarkets,
    private val mapper : DetailedCoinViewStateMapper
) : BaseViewModel() {

    private val coinsEventChannel = Channel<CoinsEvent>()
    val coinsEvent = coinsEventChannel.receiveAsFlow()

    private val sortEventChannel = Channel<SortEvent>()
    private val sortEvent = sortEventChannel.receiveAsFlow()

    val coinList = sortEvent.flatMapLatest { event ->
        val order = when(event) {
            is SortEvent.SortByMarketCap -> "market_cap_desc"
            is SortEvent.SortByPercentChange -> "market_cap_desc"
            is SortEvent.SortByPrice -> "market_cap_desc"
            is SortEvent.SortByVolume -> "volume_desc"
        }

        fetchCoinMarkets.execute(
            order = order,
            hasSparkLineNeeded = true
        ).map { pagingData ->
            pagingData.map { mapper.map(it) }
        }.cachedIn(viewModelScope)
    }

    suspend fun fetchCoinList() = fetchCoinMarkets
        .execute(
            order = "market_cap_desc",
            hasSparkLineNeeded = true
        ).map { pagingData ->
            pagingData.map { mapper.map(it) }
        }.cachedIn(viewModelScope)

    fun onStart() {
        onMarketCapFilter()
    }
    fun onCoinSelected(coin: DetailedCoinViewState) = viewModelScope.launch {
        coinsEventChannel.send(CoinsEvent.NavigateDetailScreen(coin))
    }

    fun onMarketCapFilter() = viewModelScope.launch {
        sortEventChannel.send(SortEvent.SortByMarketCap)
    }

    fun onPercentChangeFilter() = viewModelScope.launch {
        sortEventChannel.send(SortEvent.SortByPercentChange)
    }

    fun onPriceFilter() = viewModelScope.launch {
        sortEventChannel.send(SortEvent.SortByPrice)
    }

    fun onVolumeFilter() = viewModelScope.launch {
        sortEventChannel.send(SortEvent.SortByVolume)
    }

    sealed class CoinsEvent {
        data class NavigateDetailScreen(val coin: DetailedCoinViewState): CoinsEvent()
    }

    sealed class SortEvent {
        object SortByMarketCap : SortEvent()
        object SortByPercentChange : SortEvent()
        object SortByPrice : SortEvent()
        object SortByVolume : SortEvent()
    }



}