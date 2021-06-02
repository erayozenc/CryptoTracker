package com.example.cryptotracker.data.mapper

import com.example.cryptotracker.data.DataMapper
import com.example.cryptotracker.data.local.model.CoinEntity
import com.example.cryptotracker.data.network.model.DetailedCoinDataModel
import javax.inject.Inject

class CoinEntityMapper @Inject constructor() : DataMapper<DetailedCoinDataModel, CoinEntity> {

    override fun map(dto: DetailedCoinDataModel) =
            CoinEntity(
                id = dto.id.orEmpty(),
                symbol = dto.symbol.orEmpty(),
                name = dto.name.orEmpty(),
                image = dto.image.orEmpty()
            )

    override fun mapList(dataList: List<DetailedCoinDataModel>): List<CoinEntity> {
        return dataList.map { map(it) }
    }
}