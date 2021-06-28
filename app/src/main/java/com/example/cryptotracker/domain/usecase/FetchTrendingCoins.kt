package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.TrendingCoinsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTrendingCoins @Inject constructor(
        private val repository: TrendingCoinsRepository
) {
    suspend fun execute(): Flow<Resource<List<CoinDomainModel>>> = repository.getTrendingCoins()
}