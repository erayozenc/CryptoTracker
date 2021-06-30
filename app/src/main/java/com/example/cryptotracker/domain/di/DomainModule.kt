package com.example.cryptotracker.domain.di

import com.example.cryptotracker.data.local.datasource.CoinListDataSource
import com.example.cryptotracker.data.mapper.*
import com.example.cryptotracker.data.network.datasource.*
import com.example.cryptotracker.data.repository.*
import com.example.cryptotracker.domain.repository.*
import com.example.cryptotracker.domain.usecase.*
import com.example.cryptotracker.presentation.coinMarkets.DetailedCoinViewStateMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    @Singleton
    fun provideCoinMarketsRepository(
        remoteDataSource: CoinMarketsRemoteDataSource,
        mapper : DetailedCoinMapper
    ) : CoinMarketsRepository {
        return CoinMarketsRepositoryImpl(remoteDataSource, mapper)
    }

    @Provides
    @Singleton
    fun provideCoinListRepository(
        remoteDataSource: CoinListRemoteDataSource,
        mapper : CoinMapper,
    ) : CoinListRepository {
        return CoinListRepositoryImpl(remoteDataSource, mapper)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        remoteDataSource: CoinRemoteDataSource,
        mapper : CoinMapper,
    ) : CoinRepository {
        return CoinRepositoryImpl(remoteDataSource, mapper)
    }

    @Provides
    @Singleton
    fun provideFetchCoin(
        repository: CoinRepository
    ) = FetchCoin(repository)

    @Provides
    @Singleton
    fun provideFetchCoinList(
        repository: CoinMarketsRepository
    ) = FetchCoinMarkets(repository)

    @Provides
    @Singleton
    fun provideCoinViewStateMapper() = DetailedCoinViewStateMapper()

    @Provides
    @Singleton
    fun provideCoinDetailRepository(
            remoteDataSource: CoinDetailRemoteDataSource,
            mapper : CoinMarketChartMapper
    ) : CoinDetailRepository {
        return CoinDetailRepositoryImpl(remoteDataSource, mapper)
    }

    @Provides
    @Singleton
    fun provideFetchCoinMarketChart(
            repository: CoinDetailRepository
    ) = FetchCoinMarketChart(repository)

    @Provides
    @Singleton
    fun provideTrendingCoinsRepository(
            remoteDataSource: TrendingCoinsRemoteDataSource,
            mapper : CoinMapper
    ) : TrendingCoinsRepository {
        return TrendingCoinsRepositoryImpl(remoteDataSource, mapper)
    }

    @Provides
    @Singleton
    fun provideFetchTrendingCoins(
            repository: TrendingCoinsRepository
    ) = FetchTrendingCoins(repository)

    @Provides
    @Singleton
    fun provideTopTenCoinsRepository(
        remoteDataSource: TopTenCoinsRemoteDataSource,
        mapper : DetailedCoinMapper
    ) : TopTenCoinsRepository {
        return TopTenCoinsRepositoryImpl(remoteDataSource, mapper)
    }


}