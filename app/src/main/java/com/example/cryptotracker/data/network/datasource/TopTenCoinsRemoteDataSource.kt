package com.example.cryptotracker.data.network.datasource

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.network.model.DetailedCoinDataModel
import com.example.cryptotracker.data.network.model.TrendingCoinsResponse
import com.example.cryptotracker.data.network.service.ApiName
import com.example.cryptotracker.data.network.service.ApiService
import com.example.cryptotracker.data.network.service.toModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import javax.inject.Inject

class TopTenCoinsRemoteDataSource @Inject constructor(
        private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun getTopTenCoins(): ApiResult<List<DetailedCoinDataModel>> = safeApiCall {
        val queries = HashMap<String , Any?>()
        queries["vs_currency"] = "usd"
        queries["per_page"] = 10
        apiService.getData(ApiName.COINS_MARKETS, queries = queries).toList()
    }

    private fun ResponseBody.toList() : List<DetailedCoinDataModel> {
        val type = object : TypeToken<List<DetailedCoinDataModel>>() {}.type
        return Gson().fromJson(this.string(), type)
    }
}