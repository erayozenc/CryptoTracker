package com.example.cryptotracker.data.network.datasource

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.network.model.CoinDataModel
import com.example.cryptotracker.data.network.model.DetailedCoinDataModel
import com.example.cryptotracker.data.network.service.ApiName
import com.example.cryptotracker.data.network.service.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import javax.inject.Inject

class CoinListRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun getCoinList() : ApiResult<List<CoinDataModel>> = safeApiCall {
        apiService.getData(ApiName.COINS_LIST).toList()
    }

    private fun ResponseBody.toList() : List<CoinDataModel> {
        val type = object : TypeToken<List<CoinDataModel>>() {}.type
        return Gson().fromJson(this.string(), type)
    }
}