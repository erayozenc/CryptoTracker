package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCoin @Inject constructor(
    private val repository: CoinRepository
) {
    suspend fun execute(id: String): Flow<Resource<CoinDomainModel>> = repository.getCoin(id)
}