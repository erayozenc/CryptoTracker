package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinFromDatabase @Inject constructor(
        private val repository: CoinListRepository
) {
    fun execute(id: String): Flow<CoinDomainModel> = repository.getCoinFromDatabase(id)
}