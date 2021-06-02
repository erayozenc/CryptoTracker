package com.example.cryptotracker.presentation.coinList

import androidx.lifecycle.*
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.usecase.FetchCoinList
import com.example.cryptotracker.presentation.base.BaseViewModel
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Error
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

    val coinList = sortEvent.flatMapLatest {event ->
        fetchCoinList.execute()
            .onStart { _progressBar.value = true }
            .map { resource ->
                if (resource is Resource.Success) {
                    val dataModelList = when(event) {
                        SortEvent.SortByRank -> { resource.data.sortedBy { it.rank } }
                        SortEvent.SortByVolume -> { resource.data.sortedByDescending { it.volume } }
                        SortEvent.SortByMarketCap -> { resource.data.sortedByDescending { it.marketCap } }
                        SortEvent.SortByPercentChange -> { resource.data.sortedByDescending { it.changePercent } }
                        SortEvent.SortByPrice -> { resource.data.sortedByDescending { it.price } }
                    }
                    mapper.mapList(dataModelList)
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