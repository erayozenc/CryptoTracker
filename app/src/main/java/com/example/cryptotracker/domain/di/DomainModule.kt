package com.example.cryptotracker.domain.di

import com.example.cryptotracker.data.local.datasource.CoinListDataSource
import com.example.cryptotracker.data.mapper.*
import com.example.cryptotracker.data.network.datasource.CoinDetailRemoteDataSource
import com.example.cryptotracker.data.network.datasource.CoinListRemoteDataSource
import com.example.cryptotracker.data.network.datasource.TrendingCoinsRemoteDataSource
import com.example.cryptotracker.data.repository.CoinDetailRepositoryImpl
import com.example.cryptotracker.data.repository.CoinListRepositoryImpl
import com.example.cryptotracker.data.repository.TrendingCoinsRepositoryImpl
import com.example.cryptotracker.domain.repository.CoinDetailRepository
import com.example.cryptotracker.domain.repository.CoinListRepository
import com.example.cryptotracker.domain.repository.TrendingCoinsRepository
import com.example.cryptotracker.domain.usecase.*
import com.example.cryptotracker.presentation.coinList.CoinViewStateMapper
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
    fun provideCoinListRepository(
            remoteDataSource: CoinListRemoteDataSource,
            dataSource: CoinListDataSource,
            mapper : CoinMapper,
            entityMapper: CoinEntityMapper,
            entityToDomainMapper: CoinEntityToDomainMapper
    ) : CoinListRepository {
        return CoinListRepositoryImpl(remoteDataSource, dataSource, mapper, entityMapper, entityToDomainMapper)
    }

    @Provides
    @Singleton
    fun provideFetchCoinList(
        repository: CoinListRepository
    ) = FetchCoinList(repository)

    @Provides
    @Singleton
    fun provideGetCoinListFromDatabase(
            repository: CoinListRepository
    ) = GetCoinListFromDatabase(repository)

    @Provides
    @Singleton
    fun provideGetCoinFromDatabase(
            repository: CoinListRepository
    ) = GetCoinFromDatabase(repository)

    @Provides
    @Singleton
    fun provideCoinViewStateMapper() = CoinViewStateMapper()

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

}