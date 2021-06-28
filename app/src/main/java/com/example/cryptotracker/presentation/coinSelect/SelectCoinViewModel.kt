package com.example.cryptotracker.presentation.coinSelect

import androidx.lifecycle.*
import com.example.cryptotracker.domain.usecase.GetCoinListFromDatabase
import com.example.cryptotracker.presentation.common.CoinViewState
import com.example.cryptotracker.presentation.coinList.CoinViewStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SelectCoinViewModel @Inject constructor(
        private val getCoinListFromDatabase: GetCoinListFromDatabase,
        private val mapper: CoinViewStateMapper
) : ViewModel() {

    private val searchChannel = Channel<String>()
    private val searchFlow = searchChannel.receiveAsFlow()

    private val selectCoinEventsChannel = Channel<SelectCoinEvent>()
    val selectCoinEvents = selectCoinEventsChannel.receiveAsFlow()

    init {
        viewModelScope.launch { searchChannel.send("") }
    }

    val coins = searchFlow.flatMapLatest {
        getCoinListFromDatabase
                .execute(it)
                .debounce(100)
                .map {domainModels ->
                    mapper.mapList(domainModels)
                }
    }.asLiveData()

    fun onCoinSelected(coin: CoinViewState) = viewModelScope.launch {
        selectCoinEventsChannel.send(SelectCoinEvent.NavigateBackWithSelectedCoin(coin))
    }

    fun onSearchQueryChanged(query: String) = viewModelScope.launch {
        searchChannel.send(query)
    }

    sealed class SelectCoinEvent {
        data class NavigateBackWithSelectedCoin(val coin: CoinViewState): SelectCoinEvent()
    }

}