package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCoinList @Inject constructor(
    private val repository: CoinListRepository
) {
    suspend fun execute(): Flow<Resource<List<DetailedCoinDomainModel>>> = repository.getCoinList()
}