package com.example.cryptotracker.domain.repository

import androidx.paging.PagingData
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import kotlinx.coroutines.flow.Flow

interface CoinListRepository {

    suspend fun getCoinList() : Flow<PagingData<DetailedCoinDomainModel>>

    fun getCoinListFromDatabase(query: String) : Flow<List<DetailedCoinDomainModel>>

    fun getCoinFromDatabase(id: String) : Flow<DetailedCoinDomainModel>
}