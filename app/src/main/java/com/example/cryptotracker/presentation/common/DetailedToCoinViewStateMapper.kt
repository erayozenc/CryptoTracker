package com.example.cryptotracker.presentation.common

import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.presentation.base.ViewStateMapper
import com.example.cryptotracker.presentation.util.toSplitCoinPrice
import javax.inject.Inject

class DetailedToCoinViewStateMapper @Inject constructor() : ViewStateMapper<DetailedCoinDomainModel, CoinViewState> {

    override fun map(domainModel: DetailedCoinDomainModel) =
            CoinViewState(
                    id = domainModel.id,
                    name = domainModel.name,
                    image = domainModel.image,
                    symbol = domainModel.symbol,
                    score = domainModel.rank,
                    price = domainModel.price.toString().toSplitCoinPrice()
            )

    override fun mapList(domainList: List<DetailedCoinDomainModel>): List<CoinViewState> {
        return domainList.map { map(it) }
    }
}