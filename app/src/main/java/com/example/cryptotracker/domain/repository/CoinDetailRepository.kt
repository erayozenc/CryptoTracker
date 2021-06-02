package com.example.cryptotracker.domain.repository

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinMarketChartDomainModel
import kotlinx.coroutines.flow.Flow

interface CoinDetailRepository {

    suspend fun getCoinMarketChart(coinId: String, days: String, interval: String? = null) : Flow<Resource<CoinMarketChartDomainModel>>
}