package com.example.cryptotracker.data.network.datasource

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.network.model.CoinDataModel
import com.example.cryptotracker.data.network.model.coinDetail.CoinDetailDataModel
import com.example.cryptotracker.data.network.model.coinDetail.toCoinDataModel
import com.example.cryptotracker.data.network.service.ApiName
import com.example.cryptotracker.data.network.service.ApiService
import com.example.cryptotracker.data.network.service.toModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import javax.inject.Inject

class CoinRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun getCoin(id: String) : ApiResult<CoinDataModel> = safeApiCall {
        apiService.getData(ApiName.COINS_DETAIL.replace("{id}", id))
            .toModel(CoinDetailDataModel::class.java)
            .toCoinDataModel()
    }


}