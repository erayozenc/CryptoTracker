package com.example.cryptotracker.presentation.common

import androidx.recyclerview.widget.DiffUtil

object CoinComparator : DiffUtil.ItemCallback<DetailedCoinViewState>() {
    override fun areItemsTheSame(oldItem: DetailedCoinViewState, newItem: DetailedCoinViewState) =
        oldItem.symbol == newItem.symbol

    override fun areContentsTheSame(oldItem: DetailedCoinViewState, newItem: DetailedCoinViewState) =
        oldItem.hashCode() == newItem.hashCode()
}