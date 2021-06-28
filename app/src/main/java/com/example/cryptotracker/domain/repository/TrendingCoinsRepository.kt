package com.example.cryptotracker.domain.repository

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import kotlinx.coroutines.flow.Flow

interface TrendingCoinsRepository {

    suspend fun getTrendingCoins() : Flow<Resource<List<CoinDomainModel>>>
}