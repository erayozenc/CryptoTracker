package com.example.cryptotracker.data.network.datasource

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.network.model.TrendingCoinsResponse
import com.example.cryptotracker.data.network.service.ApiName
import com.example.cryptotracker.data.network.service.ApiService
import com.example.cryptotracker.data.network.service.toModel
import javax.inject.Inject

class TrendingCoinsRemoteDataSource @Inject constructor(
        private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun getTrendingCoins(): ApiResult<TrendingCoinsResponse> = safeApiCall {
        apiService.getData(ApiName.COINS_TRENDING).toModel(TrendingCoinsResponse::class.java)
    }

}