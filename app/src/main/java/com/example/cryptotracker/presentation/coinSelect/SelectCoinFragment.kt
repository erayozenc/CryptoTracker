package com.example.cryptotracker.presentation.coinSelect

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.FragmentSelectCoinBinding
import com.example.cryptotracker.presentation.base.BaseFragment
import com.example.cryptotracker.presentation.common.CoinViewState
import com.example.cryptotracker.presentation.util.onQueryTextChanged
import com.example.cryptotracker.presentation.util.setBackStackData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class SelectCoinFragment : BaseFragment<FragmentSelectCoinBinding>(), SelectCoinAdapter.OnItemClickListener {

    private val viewModel by viewModels<SelectCoinViewModel>()
    private lateinit var adapter : SelectCoinAdapter
    private lateinit var searchView: SearchView

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentSelectCoinBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.coins.observe(viewLifecycleOwner) {
            println(it)
            adapter.differ.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.selectCoinEvents.collect { event ->
                when(event) {
                    is SelectCoinViewModel.SelectCoinEvent.NavigateBackWithSelectedCoin -> {
                        setBackStackData("selectedCoin", event.coin)
                    }
                }
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setBackStackData("selectedCoin", null)
            }
        })

    }

    private fun setupRecyclerView() {
        adapter = SelectCoinAdapter(this)
        binding.rvCoins.apply {
            adapter = this@SelectCoinFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onItemClick(coin: CoinViewState) {
        viewModel.onCoinSelected(coin)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.onSearchQueryChanged(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }
}