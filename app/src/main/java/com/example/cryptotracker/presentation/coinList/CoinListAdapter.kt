package com.example.cryptotracker.presentation.coinList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotracker.databinding.ItemListingCoinBinding
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class CoinListAdapter(private val listener: OnItemClickListener)
    : PagingDataAdapter<DetailedCoinViewState, CoinListAdapter.CoinViewHolder>(CoinComparator) {

    inner class CoinViewHolder(
        private val binding: ItemListingCoinBinding,
        val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewState: DetailedCoinViewState) {
            binding.apply {
                Glide.with(ivCoinIcon).load(viewState.image).into(ivCoinIcon)
                tvCoinName.text = viewState.name
                tvCoinChangePercent.text = viewState.changePercent
                tvCoinPrice.text = "$" + viewState.price
                tvCoinSymbol.text = viewState.symbol
                ivCoinStatus.setImageResource(viewState.drawable)

                val chartStyle = SparklineChartStyle(context)
                chartStyle.styleChart(chartSparkLine)
                val lineDataSet = LineDataSet(viewState.sparkLineEntries, "SPARKLINE_CHART")
                chartStyle.styleLineDataSet(lineDataSet, viewState.color)
                binding.chartSparkLine.apply {
                    data = LineData(lineDataSet)
                    invalidate()
                }

                root.setOnClickListener {
                    listener.onItemClick(viewState)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder =
        CoinViewHolder(
            ItemListingCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
            parent.context
        )

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)
        coin?.let {
            holder.bind(it)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(coin: DetailedCoinViewState)
    }

    object CoinComparator : DiffUtil.ItemCallback<DetailedCoinViewState>() {
        override fun areItemsTheSame(oldItem: DetailedCoinViewState, newItem: DetailedCoinViewState) =
            oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(oldItem: DetailedCoinViewState, newItem: DetailedCoinViewState) =
            oldItem.hashCode() == newItem.hashCode()
    }
}