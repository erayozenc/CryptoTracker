package com.example.cryptotracker.presentation.common

import android.os.Parcelable
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedCoinViewState(
    val changePercent: String,
    val marketCap: String, //x
    val maxSupply: String, //x
    val id : String,
    val name: String,
    val image: String,
    val price: String,
    val rank: String, //x
    val supply: String, //x
    val symbol: String,
    val volume: String, //x
    val drawable: Int,
    val color: Int,
    val highestPrice24h: String,
    val lowestPrice24h: String,
    val sparkLineEntries: List<Entry>,
    val isPriceIncreasing: Boolean
) : Parcelable