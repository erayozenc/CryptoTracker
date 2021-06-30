package com.example.cryptotracker.presentation.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentHomepageBinding
import com.example.cryptotracker.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

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
        setupEventListener()

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

            buttonCompare.root.setOnClickListener {
                viewModel.onComparisonButtonClicked()
            }
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

    private fun setupEventListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homepageEvent.collect { event ->
                when(event) {
                    is HomepageViewModel.HomepageEvent.NavigateComparisonScreen -> {
                        findNavController().navigate(R.id.action_homepageFragment_to_coinComparisonFragment)
                    }
                }
            }
        }

    }
}