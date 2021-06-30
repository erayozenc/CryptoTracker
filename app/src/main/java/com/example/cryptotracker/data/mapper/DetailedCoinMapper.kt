package com.example.cryptotracker.data.mapper

import com.example.cryptotracker.data.DataMapper
import com.example.cryptotracker.data.network.model.DetailedCoinDataModel
import com.example.cryptotracker.data.network.model.Prices
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import javax.inject.Inject

class DetailedCoinMapper @Inject constructor()
    : DataMapper<DetailedCoinDataModel, DetailedCoinDomainModel> {

    override fun map(dto: DetailedCoinDataModel): DetailedCoinDomainModel =
        DetailedCoinDomainModel(
            id = dto.id.orEmpty(),
            changePercent = dto.price_change_percentage_24h.orZero(),
            marketCap = dto.market_cap.orZero(),
            maxSupply = dto.max_supply.orZero(),
            name = dto.name.orEmpty(),
            image = dto.image.orEmpty(),
            price = dto.current_price.orZero(),
            rank = dto.market_cap_rank.orZero(),
            supply = dto.total_supply.orZero(),
            symbol = dto.symbol.orEmpty(),
            volume = dto.total_volume.orZero(),
            highest24h = dto.high_24h.orZero(),
            lowest24h = dto.low_24h.orZero(),
            sparklinePrices = dto.sparkline_in_7d?.price ?: listOf()
        )

    override fun mapList(dataList: List<DetailedCoinDataModel>): List<DetailedCoinDomainModel> {
        return dataList.map { map(it) }
    }


    fun Double?.orZero(): Double = this ?: 0.0
    fun Long?.orZero(): Long = this ?: 0
    fun Int?.orZero(): Int = this ?: 0
}