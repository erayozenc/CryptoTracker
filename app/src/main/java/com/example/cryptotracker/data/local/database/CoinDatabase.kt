package com.example.cryptotracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptotracker.data.local.model.CoinEntity

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun getDao() : CoinListDao
}