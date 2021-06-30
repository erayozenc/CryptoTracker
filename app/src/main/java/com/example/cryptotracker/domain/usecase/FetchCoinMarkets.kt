package com.example.cryptotracker.domain.usecase

import androidx.paging.PagingData
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.CoinMarketsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCoinMarkets @Inject constructor(
    private val repository: CoinMarketsRepository
) {
    suspend fun execute(
        order: String,
        hasSparkLineNeeded: Boolean
    ): Flow<PagingData<DetailedCoinDomainModel>> = repository.getCoinList(order, hasSparkLineNeeded)
}