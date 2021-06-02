package com.example.cryptotracker.domain.model

data class CoinMarketChartDomainModel(
    val marketCaps: List<Pair<Double, Double>>,
    val prices: List<Pair<Double, Double>>,
    val volumes: List<Pair<Double, Double>>
)