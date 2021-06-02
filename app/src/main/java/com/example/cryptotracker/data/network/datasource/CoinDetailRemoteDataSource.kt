package com.example.cryptotracker.data.network.datasource

import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.network.model.CoinMarketChartResponse
import com.example.cryptotracker.data.network.service.ApiName
import com.example.cryptotracker.data.network.service.ApiService
import com.example.cryptotracker.data.network.service.toModel
import javax.inject.Inject

class CoinDetailRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun getMarketChart(
        coinId : String,
        currency: String = "usd",
        days: String = "14",
        interval: String? = null
    ) : ApiResult<CoinMarketChartResponse> = safeApiCall {
        val queries = HashMap<String, Any?>().apply {
            put("vs_currency", currency)
            put("days", days)
            if (!interval.isNullOrEmpty())  put("interval", interval)
        }
        val data = apiService.getData(
            url = ApiName.COINS_MARKET_CHART.replace("{id}",coinId),
            queries = queries
        )
        data.toModel(CoinMarketChartResponse::class.java)
    }
}