package com.example.cryptotracker.domain.usecase

import androidx.paging.PagingData
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCoinList @Inject constructor(
    private val repository: CoinListRepository
) {
    suspend fun execute(): Flow<PagingData<DetailedCoinDomainModel>> = repository.getCoinList()
}