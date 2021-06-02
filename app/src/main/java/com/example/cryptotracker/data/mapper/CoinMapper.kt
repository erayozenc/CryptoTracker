package com.example.cryptotracker.data.mapper

import com.example.cryptotracker.data.DataMapper
import com.example.cryptotracker.data.network.model.CoinDataModel
import com.example.cryptotracker.domain.model.CoinDomainModel
import javax.inject.Inject

class CoinMapper @Inject constructor() : DataMapper<CoinDataModel, CoinDomainModel> {
    override fun map(dto: CoinDataModel) =
            CoinDomainModel(
                id = dto.id.orEmpty(),
                name = dto.name.orEmpty(),
                symbol = dto.symbol.orEmpty(),
                image = dto.small.orEmpty(),
                score = dto.score ?: 0,
                priceBtc = dto.price_btc ?: 0.0
            )

    override fun mapList(dataList: List<CoinDataModel>): List<CoinDomainModel> {
        return dataList.map { map(it) }
    }

}