package com.example.cryptotracker.domain.repository

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.model.CoinMarketChartDomainModel
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import kotlinx.coroutines.flow.Flow

interface TopTenCoinsRepository {

    suspend fun getTopTenCoins() : Flow<Resource<List<DetailedCoinDomainModel>>>
}