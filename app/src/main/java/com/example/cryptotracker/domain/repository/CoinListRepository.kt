package com.example.cryptotracker.domain.repository

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import kotlinx.coroutines.flow.Flow

interface CoinListRepository {

    suspend fun getCoinList() : Flow<Resource<List<DetailedCoinDomainModel>>>

    fun getCoinListFromDatabase(query: String) : Flow<List<DetailedCoinDomainModel>>

    fun getCoinFromDatabase(id: String) : Flow<DetailedCoinDomainModel>
}