package com.example.cryptotracker.data.di

import android.content.Context
import androidx.room.Room
import com.example.cryptotracker.data.local.LocalConstant.COIN_DATABASE_NAME
import com.example.cryptotracker.data.local.database.CoinDatabase
import com.example.cryptotracker.data.local.database.CoinListDao
import com.example.cryptotracker.data.local.datasource.CoinListDataSource
import com.example.cryptotracker.data.mapper.CoinEntityMapper
import com.example.cryptotracker.data.mapper.CoinEntityToDomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideCoinDatabase(
            @ApplicationContext app: Context
    ) = Room.databaseBuilder(
            app,
            CoinDatabase::class.java,
            COIN_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCoinDao(db: CoinDatabase) = db.getDao()

    @Provides
    @Singleton
    fun provideCoinEntityMapper() = CoinEntityMapper()

    @Provides
    @Singleton
    fun provideCoinListDataSource(dao : CoinListDao) = CoinListDataSource(dao)

    @Provides
    @Singleton
    fun provideCoinEntityToDomainMapper() = CoinEntityToDomainMapper()
}