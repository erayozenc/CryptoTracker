package com.example.cryptotracker.data.repository

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.mapper.CoinMapper
import com.example.cryptotracker.data.mapper.DetailedCoinMapper
import com.example.cryptotracker.data.network.datasource.TopTenCoinsRemoteDataSource
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.TopTenCoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopTenCoinsRepositoryImpl(
    private val remoteDataSource: TopTenCoinsRemoteDataSource,
    private val mapper: DetailedCoinMapper
) : TopTenCoinsRepository {

    override suspend fun getTopTenCoins(): Flow<Resource<List<DetailedCoinDomainModel>>> = flow {
        when (val result = remoteDataSource.getTopTenCoins()) {
            is ApiResult.Success -> {
                emit(Resource.Success(mapper.mapList(result.value)))
            }

            is ApiResult.Failure -> {
                emit(Resource.Error(result.errorBody.toString(), null))
            }
        }
    }


}