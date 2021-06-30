package com.example.cryptotracker.domain.repository

import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository{

    suspend fun getCoin(id: String) : Flow<Resource<CoinDomainModel>>
}