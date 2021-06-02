package com.example.cryptotracker.domain.model

data class DetailedCoinDomainModel(
    val changePercent: Double,
    val id: String,
    val marketCap: Double,
    val maxSupply: Double,
    val name: String,
    val image: String,
    val price: Double,
    val rank: Int,
    val supply: Double,
    val symbol: String,
    val volume: Double,
    val highest24h: Double,
    val lowest24h: Double,
    val sparklinePrices: List<Double>
)