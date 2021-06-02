package com.example.cryptotracker.data.local.datasource

import com.example.cryptotracker.data.local.database.CoinListDao
import com.example.cryptotracker.data.local.model.CoinEntity
import javax.inject.Inject

class CoinListDataSource @Inject constructor(
        private val dao: CoinListDao
) {

    suspend fun insert(coinList : List<CoinEntity>) = dao.insert(coinList)

    fun getCoins(query: String)  = dao.getCoins(query)

    fun getCoin(id: String) = dao.getCoin(id)
}