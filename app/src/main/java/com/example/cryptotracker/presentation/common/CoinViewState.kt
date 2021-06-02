package com.example.cryptotracker.presentation.common

data class CoinViewState(
        val id: String,
        val name: String,
        val score: Int,
        val image: String,
        val symbol: String,
        val price: String
)