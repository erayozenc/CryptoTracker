package com.example.cryptotracker.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cryptotracker.data.mapper.DetailedCoinMapper
import com.example.cryptotracker.data.network.datasource.CoinMarketsRemoteDataSource
import com.example.cryptotracker.domain.model.DetailedCoinDomainModel
import javax.inject.Inject

class CoinMarketsPagingSource @Inject constructor(
    private val remoteDataSource: CoinMarketsRemoteDataSource,
    private val mapper: DetailedCoinMapper
): PagingSource<Int, DetailedCoinDomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DetailedCoinDomainModel> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val result = remoteDataSource.getCoinList(position, params.loadSize)

        return when(result) {
            is ApiResult.Success -> {
                val nextKey = if (result.value.isEmpty()) {
                    null
                } else {
                    position + (params.loadSize / 10)
                }

                LoadResult.Page(
                    data = result.value.map { mapper.map(it) },
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
            }
            is ApiResult.Failure -> {
                LoadResult.Error(Throwable(result.errorBody.toString()))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DetailedCoinDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        val STARTING_PAGE_INDEX = 1
    }

}