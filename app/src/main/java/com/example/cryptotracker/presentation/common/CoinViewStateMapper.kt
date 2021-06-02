package com.example.cryptotracker.presentation.common

import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.presentation.base.ViewStateMapper
import com.example.cryptotracker.presentation.util.toSplitCoinPrice
import javax.inject.Inject

class CoinViewStateMapper @Inject constructor(): ViewStateMapper<CoinDomainModel, CoinViewState> {

    override fun map(domainModel: CoinDomainModel) =
            CoinViewState(
                    id = domainModel.id,
                    name = domainModel.name,
                    symbol = domainModel.symbol,
                    score = domainModel.score,
                    image = domainModel.image,
                    price = domainModel.priceBtc.toString().toSplitCoinPrice()
            )

    override fun mapList(domainList: List<CoinDomainModel>): List<CoinViewState> {
        return domainList.map { map(it) }
    }
}