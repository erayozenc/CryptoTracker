package com.example.cryptotracker.domain.repository

import androidx.paging.PagingData
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import kotlinx.coroutines.flow.Flow

interface CoinListRepository {

    suspend fun getCoinList() : Flow<Resource<List<CoinDomainModel>>>
}