package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinFromDatabase @Inject constructor(
        private val repository: CoinListRepository
) {
    fun execute(id: String): Flow<DetailedCoinDomainModel> = repository.getCoinFromDatabase(id)
}