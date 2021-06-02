package com.example.cryptotracker.presentation.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotracker.databinding.FragmentHomepageBinding
import com.example.cryptotracker.presentation.base.BaseFragment
import com.example.cryptotracker.presentation.common.CoinViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomepageFragment : BaseFragment<FragmentHomepageBinding>() {

    private val viewModel by viewModels<HomepageViewModel>()
    private lateinit var trendingAdapter: CoinPreviewAdapter
    private lateinit var topCoinsAdapter: CoinPreviewAdapter

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentHomepageBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        viewModel.trendingCoins.observe(viewLifecycleOwner) {
            trendingAdapter.submitList(it)
        }

        viewModel.topCoins.observe(viewLifecycleOwner) {
            topCoinsAdapter.submitList(it)
        }

    }

    private fun initViews() {
        binding.apply {
            buttonCompare.tvName.text = "Compare"
            buttonConvert.tvName.text = "Convert"
            titleTrendingCoins.tvTitle.text = "Trending Coins"
            titleTopCoins.tvTitle.text = "Top 10 Coins"
        }

        setupTrendingRecyclerView()
        setupTopCoinsRecyclerView()
    }

    private fun setupTrendingRecyclerView() {
        trendingAdapter = CoinPreviewAdapter()
        binding.rvTrendingCoins.apply {
            adapter = trendingAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupTopCoinsRecyclerView() {
        topCoinsAdapter = CoinPreviewAdapter()
        binding.rvTopCoins.apply {
            adapter = topCoinsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}