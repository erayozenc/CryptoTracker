package com.example.cryptotracker.presentation.coinSelect

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.usecase.FetchCoinList
import com.example.cryptotracker.domain.usecase.FetchCoinMarkets
import com.example.cryptotracker.presentation.base.BaseViewModel
import com.example.cryptotracker.presentation.coinMarkets.DetailedCoinViewStateMapper
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SelectCoinViewModel @Inject constructor(
        private val fetchCoinMarkets: FetchCoinMarkets,
        private val mapper: DetailedCoinViewStateMapper
) : BaseViewModel() {

    private val searchChannel = Channel<String>()
    private val searchFlow = searchChannel.receiveAsFlow()

    private val selectCoinEventsChannel = Channel<SelectCoinEvent>()
    val selectCoinEvents = selectCoinEventsChannel.receiveAsFlow()

    init {
        viewModelScope.launch { searchChannel.send("") }
    }

    val coinList = searchFlow.flatMapLatest { event ->
        fetchCoinMarkets.execute()
            .map { pagingData ->
                pagingData.map { mapper.map(it) }
            }.cachedIn(viewModelScope)
    }

    suspend fun fetchCoinList() =
        fetchCoinMarkets.execute()
        .map { pagingData ->
            pagingData.map { mapper.map(it) }
        }.cachedIn(viewModelScope)


    fun onCoinSelected(coin: DetailedCoinViewState) = viewModelScope.launch {
        selectCoinEventsChannel.send(SelectCoinEvent.NavigateBackWithSelectedCoin(coin))
    }

    fun onSearchQueryChanged(query: String) = viewModelScope.launch {
        searchChannel.send(query)
    }

    sealed class SelectCoinEvent {
        data class NavigateBackWithSelectedCoin(val coin: DetailedCoinViewState): SelectCoinEvent()
    }

}