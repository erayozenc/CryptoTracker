package com.example.cryptotracker.presentation.coinList

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.cryptotracker.domain.usecase.FetchCoinList
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
class CoinListViewModel @Inject constructor(
    private val fetchCoinList: FetchCoinList,
    private val mapper : DetailedCoinViewStateMapper
) : BaseViewModel() {

    private val coinsEventChannel = Channel<CoinsEvent>()
    val coinsEvent = coinsEventChannel.receiveAsFlow()

    private val sortEventChannel = Channel<SortEvent>()
    private val sortEvent = sortEventChannel.receiveAsFlow()

    val coinList = sortEvent.flatMapLatest { event ->
        fetchCoinList.execute()
            .map { pagingData ->
                /*val dataModelList = when(event) {
                    SortEvent.SortByRank -> {  }
                    SortEvent.SortByVolume -> { resource.data.sortedByDescending { it.volume } }
                    SortEvent.SortByMarketCap -> { resource.data.sortedByDescending { it.marketCap } }
                    SortEvent.SortByPercentChange -> { resource.data.sortedByDescending { it.changePercent } }
                    SortEvent.SortByPrice -> { resource.data.sortedByDescending { it.price } }
                }*/
                pagingData.map { mapper.map(it) }
            }
            .cachedIn(viewModelScope)
    }

    init {
        onRankSort()
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

    private fun onRankSort() = viewModelScope.launch {
        sortEventChannel.send(SortEvent.SortByRank)
    }

    sealed class CoinsEvent {
        data class NavigateDetailScreen(val coin: DetailedCoinViewState): CoinsEvent()
    }

    sealed class SortEvent {
        object SortByMarketCap : SortEvent()
        object SortByPercentChange : SortEvent()
        object SortByPrice : SortEvent()
        object SortByVolume : SortEvent()
        object SortByRank : SortEvent()
    }



}