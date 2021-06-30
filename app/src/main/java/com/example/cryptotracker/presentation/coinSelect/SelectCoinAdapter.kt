package com.example.cryptotracker.presentation.coinSelect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotracker.databinding.ItemSelectingCoinBinding
import com.example.cryptotracker.presentation.common.CoinComparator
import com.example.cryptotracker.presentation.common.DetailedCoinViewState

class SelectCoinAdapter(private val listener: OnItemClickListener)
: PagingDataAdapter<DetailedCoinViewState, SelectCoinAdapter.SelectCoinViewHolder>(CoinComparator){

    inner class SelectCoinViewHolder(private val binding: ItemSelectingCoinBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewState: DetailedCoinViewState) {
            binding.apply {
                Glide.with(ivIcon).load(viewState.image).into(ivIcon)
                tvName.text = viewState.name
                tvSymbol.text = viewState.symbol

                root.setOnClickListener {
                    listener.onItemClick(viewState)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SelectCoinViewHolder(
                ItemSelectingCoinBinding.inflate(
                    LayoutInflater.from(parent.context),
                        parent,
                        false
                )
            )

    override fun onBindViewHolder(holder: SelectCoinViewHolder, position: Int) {
        val coin = getItem(position)
        coin?.let {
            holder.bind(coin)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(coin: DetailedCoinViewState)
    }

}



