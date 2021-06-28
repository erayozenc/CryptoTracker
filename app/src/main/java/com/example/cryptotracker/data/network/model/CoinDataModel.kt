package com.example.cryptotracker.data.network.model

data class CoinDataModel(
    val current_price: Double?, //a
    val high_24h: Double?, //a
    val id: String?,
    val image: String?,
    val low_24h: Double?, //a
    val market_cap: Double?, //a
    val market_cap_change_24h: Double?, //x
    val market_cap_change_percentage_24h: Double?,
    val market_cap_rank: Int?,
    val max_supply: Double?, //a
    val name: String?, //A
    val price_change_24h: Double?,
    val price_change_percentage_24h: Double?, //a
    val symbol: String?, //a
    val total_supply: Double?, //a
    val total_volume: Double?, //a
    val sparkline_in_7d: Prices
)