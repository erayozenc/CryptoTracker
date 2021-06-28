package com.example.cryptotracker.presentation.coinList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotracker.databinding.ItemListingCoinBinding
import com.example.cryptotracker.presentation.common.CoinViewState
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class CoinListAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {

    inner class CoinViewHolder(private val binding: ItemListingCoinBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        listener.onItemClick(differ.currentList[adapterPosition])
                }
            }
        }

        fun bind(viewState: CoinViewState) {
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
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CoinViewState>(){
        override fun areItemsTheSame(oldItem: CoinViewState, newItem: CoinViewState): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: CoinViewState, newItem: CoinViewState): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder =
        CoinViewHolder(
            ItemListingCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false),
            parent.context
        )

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    interface OnItemClickListener {
        fun onItemClick(coin: CoinViewState)
    }
}