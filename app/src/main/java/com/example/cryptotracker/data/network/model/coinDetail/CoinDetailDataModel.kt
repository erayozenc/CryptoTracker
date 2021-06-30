package com.example.cryptotracker.data.network.model.coinDetail


import com.example.cryptotracker.data.network.model.CoinDataModel

data class CoinDetailDataModel(
    val id: String?,
    val name: String?,
    val marketData: MarketData?,
    val score: Int?,
    val image: Image?,
    val symbol: String?
)

fun CoinDetailDataModel.toCoinDataModel() =
    CoinDataModel(
        id = id,
        name = name,
        price_btc = marketData?.currentPrice?.price_btc,
        score = score,
        small = image?.small,
        symbol = symbol,
    )