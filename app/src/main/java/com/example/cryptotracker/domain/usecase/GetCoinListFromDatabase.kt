package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinListFromDatabase @Inject constructor(
        private val repository: CoinListRepository
) {
    fun execute(query: String): Flow<List<CoinDomainModel>> = repository.getCoinListFromDatabase(query)
}