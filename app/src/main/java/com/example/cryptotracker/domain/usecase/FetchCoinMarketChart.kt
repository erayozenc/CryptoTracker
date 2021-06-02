package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinMarketChartDomainModel
import com.example.cryptotracker.domain.repository.CoinDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCoinMarketChart @Inject constructor(
    private val repository: CoinDetailRepository
) {
    suspend fun execute(coinId: String, days: String, interval: String? = null) : Flow<Resource<CoinMarketChartDomainModel>> = repository.getCoinMarketChart(coinId, days, interval)

}