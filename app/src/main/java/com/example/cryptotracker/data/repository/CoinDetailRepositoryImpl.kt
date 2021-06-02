package com.example.cryptotracker.data.repository

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.mapper.CoinMarketChartMapper
import com.example.cryptotracker.data.network.datasource.CoinDetailRemoteDataSource
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinMarketChartDomainModel
import com.example.cryptotracker.domain.repository.CoinDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoinDetailRemoteDataSource,
    private val mapper: CoinMarketChartMapper
): CoinDetailRepository {

    override suspend fun getCoinMarketChart(
        coinId: String,
        days: String,
        interval: String?
    ): Flow<Resource<CoinMarketChartDomainModel>> = flow {
        when(val result = remoteDataSource.getMarketChart(coinId,days = days,interval = interval)) {
            is ApiResult.Success -> {
                emit(Resource.Success(mapper.map(result.value)))
            }

            is ApiResult.Failure -> {
                emit(Resource.Error(result.errorBody.toString(), null))
            }
        }
    }


}