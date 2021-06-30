package com.example.cryptotracker.presentation.homepage

import androidx.lifecycle.*
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.usecase.FetchCoinMarkets
import com.example.cryptotracker.domain.usecase.FetchCoinMarketChart
import com.example.cryptotracker.domain.usecase.FetchTopTenCoins
import com.example.cryptotracker.domain.usecase.FetchTrendingCoins
import com.example.cryptotracker.presentation.base.BaseViewModel
import com.example.cryptotracker.presentation.common.CoinViewState
import com.example.cryptotracker.presentation.common.CoinViewStateMapper
import com.example.cryptotracker.presentation.common.DetailedToCoinViewStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.lang.Error
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomepageViewModel @Inject constructor(
    private val fetchTrendingCoins: FetchTrendingCoins,
    private val fetchTopTenCoins: FetchTopTenCoins,
    private val mapper: CoinViewStateMapper,
    private val fetchCoinMarketChart: FetchCoinMarketChart,
    private val detailedMapper: DetailedToCoinViewStateMapper
): BaseViewModel() {

    private var currentBtcPrice = 0.0

    private val homepageEventChannel = Channel<HomepageEvent>()
    val homepageEvent = homepageEventChannel.receiveAsFlow()

    private val _trendingCoins : MutableLiveData<List<CoinViewState>> = MutableLiveData()
    val trendingCoins : LiveData<List<CoinViewState>> = _trendingCoins

    private val _topCoins : MutableLiveData<List<CoinViewState>> = MutableLiveData()
    val topCoins : LiveData<List<CoinViewState>> = _topCoins

    init {
        viewModelScope.launch {

            val job1 = async {
                fetchCoinMarketChart
                    .execute("bitcoin","1","daily")
                    .collect { resource ->
                        if (resource is Resource.Success)
                            currentBtcPrice = resource.data.prices[1].first
                    }
            }

            job1.await()

            val job2 = async {
                viewModelScope.launch {
                    getTopTenCoins().collect { _topCoins.postValue(it)}
                }
                viewModelScope.launch {
                    getTrendingCoins().collect { _trendingCoins.postValue(it)}
                }
            }
            
            job2.await()

        }
    }

    fun onComparisonButtonClicked() = viewModelScope.launch {
        homepageEventChannel.send(HomepageEvent.NavigateComparisonScreen)
    }

    private suspend fun getTrendingCoins() = fetchTrendingCoins
        .execute()
        .map { resource ->
            if (resource is Resource.Success) {
                resource.data.map {
                    print("ANAM ANAM " + currentBtcPrice)
                    it.priceBtc *= currentBtcPrice }
                mapper.mapList(resource.data.sortedBy { it.score })
            } else {
                val error = resource as Resource.Error
                throw Error(error.message)
            }
        }.onCompletion { _progressBar.postValue(false) }
        .catch { cause: Throwable ->
            println(cause.message)
            _snackbar.postValue(cause.message)
        }

    private suspend fun getTopTenCoins() = fetchTopTenCoins
        .execute()
        .map { resource ->
            if (resource is Resource.Success) {
                detailedMapper.mapList(resource.data.sortedBy {
                    it.rank
                }.subList(0, 10))
            } else {
                val error = resource as Resource.Error
                throw Error(error.message)
            }
        }.onCompletion { _progressBar.postValue(false) }
        .catch { cause: Throwable ->
            _snackbar.postValue(cause.message)
            println(cause.message)
        }

    sealed class HomepageEvent {
        object NavigateComparisonScreen: HomepageEvent()
    }

}