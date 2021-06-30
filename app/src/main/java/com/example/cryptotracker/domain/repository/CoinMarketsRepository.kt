package com.example.cryptotracker.domain.repository

import androidx.paging.PagingData
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import kotlinx.coroutines.flow.Flow

interface CoinMarketsRepository {

    suspend fun getCoinList() : Flow<PagingData<DetailedCoinDomainModel>>
}