package com.example.cryptotracker.data.repository

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.mapper.CoinMapper
import com.example.cryptotracker.data.network.datasource.TrendingCoinsRemoteDataSource
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.TrendingCoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrendingCoinsRepositoryImpl @Inject constructor(
        private val remoteDataSource: TrendingCoinsRemoteDataSource,
        private val mapper: CoinMapper
) : TrendingCoinsRepository{

    override suspend fun getTrendingCoins(): Flow<Resource<List<CoinDomainModel>>> = flow {
        when(val result = remoteDataSource.getTrendingCoins()) {
            is ApiResult.Success -> {
                println("12")
                emit(Resource.Success(result.value.coins.map { mapper.map(it.item) }))
            }
            is ApiResult.Failure -> {
                emit(Resource.Error(result.errorBody.toString(), null))
            }
        }
    }


}