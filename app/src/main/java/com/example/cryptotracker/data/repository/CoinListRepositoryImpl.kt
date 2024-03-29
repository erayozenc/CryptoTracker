package com.example.cryptotracker.data.repository

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.mapper.CoinMapper
import com.example.cryptotracker.data.network.datasource.CoinListRemoteDataSource
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoinListRepositoryImpl(
    private val remoteDataSource: CoinListRemoteDataSource,
    private val mapper: CoinMapper
) : CoinListRepository {

    override suspend fun getCoinList(): Flow<Resource<List<CoinDomainModel>>> = flow {
        when (val result = remoteDataSource.getCoinList()) {
            is ApiResult.Success -> {
                emit(Resource.Success(mapper.mapList(result.value)))
            }

            is ApiResult.Failure -> {
                emit(Resource.Error(result.errorBody.toString(), null))
            }
        }
    }
}