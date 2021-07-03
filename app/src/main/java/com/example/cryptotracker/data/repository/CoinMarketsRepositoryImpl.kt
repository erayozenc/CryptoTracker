package com.example.cryptotracker.data.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptotracker.data.CoinMarketsPagingSource
import com.example.cryptotracker.data.mapper.DetailedCoinMapper
import com.example.cryptotracker.data.network.datasource.CoinMarketsRemoteDataSource
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import com.example.cryptotracker.domain.repository.CoinMarketsRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class CoinMarketsRepositoryImpl @Inject constructor(
    private val remoteDataSource: CoinMarketsRemoteDataSource,
    private val mapper: DetailedCoinMapper
) : CoinMarketsRepository {

    override suspend fun getCoinList(
        order: String,
        hasSparkLineNeeded: Boolean
    ) : Flow<PagingData<DetailedCoinDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CoinMarketsPagingSource(
                    remoteDataSource,
                    mapper,
                    order,
                    hasSparkLineNeeded
                )
            }
        ).flow
    }

}