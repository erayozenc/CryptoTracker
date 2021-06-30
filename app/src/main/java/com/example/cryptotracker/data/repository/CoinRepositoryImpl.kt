package com.example.cryptotracker.data.repository

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.mapper.CoinMapper
import com.example.cryptotracker.data.network.datasource.CoinRemoteDataSource
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CoinRepositoryImpl(
    private val remoteDataSource: CoinRemoteDataSource,
    private val mapper: CoinMapper
) : CoinRepository {

    override suspend fun getCoin(id: String): Flow<Resource<CoinDomainModel>> = flow {
        when (val result = remoteDataSource.getCoin(id)) {
            is ApiResult.Success -> {
                emit(Resource.Success(mapper.map(result.value)))
            }

            is ApiResult.Failure -> {
                emit(Resource.Error(result.errorBody.toString(), null))
            }
        }
    }
}