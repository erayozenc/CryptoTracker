package com.example.cryptotracker.presentation.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotracker.databinding.ItemCoinPreviewBinding
import com.example.cryptotracker.presentation.common.CoinViewState

class CoinPreviewAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<CoinPreviewAdapter.CoinPreviewViewHolder>() {

    private val list: MutableList<CoinViewState> = mutableListOf()

    fun submitList(coins: List<CoinViewState>) {
        list.addAll(coins)
        notifyDataSetChanged()
    }

    inner class CoinPreviewViewHolder(private val binding: ItemCoinPreviewBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        listener.onItemClick(list[adapterPosition])
                }
            }
        }

        fun bind(viewState: CoinViewState) {
            binding.apply {
                Glide.with(ivIcon).load(viewState.image).into(ivIcon)
                tvName.text = viewState.name
                tvPrice.text = "$ ${viewState.price}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CoinPreviewViewHolder(
                    ItemCoinPreviewBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: CoinPreviewViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size

    interface OnItemClickListener {
        fun onItemClick(coin: CoinViewState)
    }
}