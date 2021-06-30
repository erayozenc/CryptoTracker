package com.example.cryptotracker.presentation.coinMarkets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentCoinListBinding
import com.example.cryptotracker.presentation.base.BaseFragment
import com.example.cryptotracker.presentation.common.DetailedCoinViewState
import com.example.cryptotracker.presentation.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class CoinMarketsFragment : BaseFragment<FragmentCoinListBinding>(), CoinMarketsAdapter.OnItemClickListener {

    private lateinit var adapter : CoinMarketsAdapter
    private val viewModel by viewModels<CoinMarketsViewModel>()

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoinListBinding = FragmentCoinListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        setupObservers()
        setupEventListener()

        lifecycleScope.launch {
            viewModel.fetchCoinList().collectLatest {
                adapter.submitData(it)
            }
        }

    }

    private fun setupRecyclerView() {
        adapter = CoinMarketsAdapter(this)
        binding.rvCoin.apply {
            adapter = this@CoinMarketsFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        binding.chipCap.setOnClickListener { viewModel.onMarketCapFilter() }
        binding.chipChange.setOnClickListener { viewModel.onPercentChangeFilter() }
        binding.chipPrice.setOnClickListener { viewModel.onPriceFilter() }
        binding.chipVolume.setOnClickListener { viewModel.onVolumeFilter() }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.coinList.collectLatest {
                adapter.apply {
                    loadStateFlow
                        .distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { binding.rvCoin.scrollToPosition(0) }

                    submitData(it)
                }
            }
        }

        viewModel.progressBar.observe(viewLifecycleOwner) { isProgress ->
            showDialog(isProgress)
        }

        viewModel.snackbar.observe(viewLifecycleOwner) { message ->
            requireView().snackbar(message)
        }
    }

    private fun setupEventListener() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.coinsEvent.collect { event ->
                when(event) {
                    is CoinMarketsViewModel.CoinsEvent.NavigateDetailScreen -> {
                        val bundle = Bundle().apply {
                            putParcelable("coin", event.coin)
                        }
                        findNavController().navigate(R.id.action_coinListFragment_to_coinDetailFragment, bundle)
                    }
                }
            }
        }
    }

    override fun onItemClick(coin: DetailedCoinViewState) {
        viewModel.onCoinSelected(coin)
    }
}