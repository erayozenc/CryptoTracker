package com.example.cryptotracker.domain.usecase

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.TopTenCoinsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTopTenCoins @Inject constructor(
    private val repository: TopTenCoinsRepository
) {
    suspend fun execute(): Flow<Resource<List<DetailedCoinDomainModel>>> = repository.getTopTenCoins()
}