package com.example.cryptotracker.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptotracker.data.local.model.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinListDao {

    @Query("SELECT * FROM coin_list WHERE symbol OR name LIKE '%' || :query || '%'")
    fun getCoins(query: String): Flow<List<CoinEntity>>

    @Query("SELECT * FROM coin_list WHERE id = :id ")
    fun getCoin(id: String): Flow<CoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coinList: List<CoinEntity>)
}