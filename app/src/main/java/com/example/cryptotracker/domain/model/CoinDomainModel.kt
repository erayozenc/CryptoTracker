package com.example.cryptotracker.domain.model

data class CoinDomainModel(
        val id: String,
        val name: String,
        val score: Int,
        val image: String,
        val symbol: String,
        var priceBtc: Double
)