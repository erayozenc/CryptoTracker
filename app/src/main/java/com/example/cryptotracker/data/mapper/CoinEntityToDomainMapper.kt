package com.example.cryptotracker.data.mapper

import com.example.cryptotracker.data.DataMapper
import com.example.cryptotracker.data.local.model.CoinEntity
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel

class CoinEntityToDomainMapper : DataMapper<CoinEntity, DetailedCoinDomainModel> {

    override fun map(dto: CoinEntity) =
            DetailedCoinDomainModel(
                    id = dto.id,
                    changePercent = 0.0,
                    marketCap = 0.0,
                    maxSupply = 0.0,
                    name = dto.name,
                    image = dto.image,
                    price = 0.0,
                    rank = 0,
                    supply = 0.0,
                    symbol = dto.symbol,
                    volume = 0.0,
                    highest24h = 0.0,
                    lowest24h = 0.0,
                    sparklinePrices = listOf()
            )

    override fun mapList(dataList: List<CoinEntity>): List<DetailedCoinDomainModel> {
        return dataList.map { map(it) }
    }
}