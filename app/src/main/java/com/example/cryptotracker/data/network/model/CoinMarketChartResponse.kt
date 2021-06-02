package com.example.cryptotracker.data.network.model

data class CoinMarketChartResponse(
    val market_caps: List<DoubleArray>,
    val prices: List<DoubleArray>,
    val total_volumes: List<DoubleArray>
)