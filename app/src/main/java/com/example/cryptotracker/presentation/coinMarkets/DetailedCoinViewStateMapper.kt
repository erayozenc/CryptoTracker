package com.example.cryptotracker.presentation.coinMarkets

import com.example.cryptotracker.R
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.presentation.base.ViewStateMapper
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import com.example.cryptotracker.presentation.util.toSplitChangeRate
import com.example.cryptotracker.presentation.util.toSplitCoinPrice
import com.github.mikephil.charting.data.Entry
import java.util.*
import javax.inject.Inject

class DetailedCoinViewStateMapper @Inject constructor(
): ViewStateMapper<DetailedCoinDomainModel, DetailedCoinViewState> {

    override fun map(domainModel: DetailedCoinDomainModel): DetailedCoinViewState =
        DetailedCoinViewState(
            changePercent = domainModel.changePercent.toSplitChangeRate(),
            marketCap = domainModel.marketCap.toString(),
            maxSupply = domainModel.maxSupply.toString(),
            name = domainModel.name,
            id = domainModel.id,
            image = domainModel.image,
            price = domainModel.price.toString().toSplitCoinPrice(),
            rank = domainModel.rank.toString(),
            supply = domainModel.supply.toString(),
            symbol = domainModel.symbol.toUpperCase(Locale.ROOT),
            volume = domainModel.volume.toString(),
            drawable = if (domainModel.changePercent < 0) R.drawable.ic_low_arrow else R.drawable.ic_high_arrow,
            color = if (domainModel.changePercent < 0) R.color.red else R.color.green,
            highestPrice24h = domainModel.highest24h.toString().toSplitCoinPrice(),
            lowestPrice24h = domainModel.lowest24h.toString().toSplitCoinPrice(),
            sparkLineEntries = domainModel.sparklinePrices.mapIndexed { index, d -> Entry(index.toFloat(), d.toFloat()) },
            isPriceIncreasing = domainModel.changePercent > 0
        )

    override fun mapList(domainList: List<DetailedCoinDomainModel>): List<DetailedCoinViewState> {
        return domainList.map {
            map(it)
        }
    }

}