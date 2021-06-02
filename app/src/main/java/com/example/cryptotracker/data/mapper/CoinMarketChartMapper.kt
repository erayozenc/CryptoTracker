package com.example.cryptotracker.data.mapper

import com.example.cryptotracker.data.DataMapper
import com.example.cryptotracker.data.network.model.CoinMarketChartResponse
import com.example.cryptotracker.domain.model.CoinMarketChartDomainModel
import javax.inject.Inject

class CoinMarketChartMapper @Inject constructor() : DataMapper<CoinMarketChartResponse, CoinMarketChartDomainModel> {

    override fun map(dto: CoinMarketChartResponse) =
        CoinMarketChartDomainModel(
            marketCaps = dto.market_caps.map { Pair(it[1], it[0]) },
            prices = dto.prices.map { Pair(it[1], it[0]) },
            volumes = dto.total_volumes.map { Pair(it[1], it[0]) }
        )

    override fun mapList(dataList: List<CoinMarketChartResponse>): List<CoinMarketChartDomainModel> {
        return dataList.map {
            map(it)
        }
    }

}