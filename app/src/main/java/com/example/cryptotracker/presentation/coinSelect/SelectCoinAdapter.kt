package com.example.cryptotracker.presentation.coinSelect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotracker.databinding.ItemSelectingCoinBinding
import com.example.cryptotracker.presentation.common.DetailedCoinViewState

class SelectCoinAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<SelectCoinAdapter.SelectCoinViewHolder>() {

    inner class SelectCoinViewHolder(private val binding: ItemSelectingCoinBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        listener.onItemClick(differ.currentList[adapterPosition])
                }
            }
        }

        fun bind(viewState: DetailedCoinViewState) {
            binding.apply {
                Glide.with(ivIcon).load(viewState.image).into(ivIcon)
                tvName.text = viewState.name
                tvSymbol.text = viewState.symbol
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<DetailedCoinViewState>(){
        override fun areItemsTheSame(oldItem: DetailedCoinViewState, newItem: DetailedCoinViewState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailedCoinViewState, newItem: DetailedCoinViewState): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            SelectCoinViewHolder(
                ItemSelectingCoinBinding.inflate(
                    LayoutInflater.from(parent.context),
                        parent,
                        false
                )
            )

    override fun onBindViewHolder(holder: SelectCoinViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    interface OnItemClickListener {
        fun onItemClick(coin: DetailedCoinViewState)
    }


}



