package com.example.cryptotracker.presentation.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.example.cryptotracker.presentation.util.ProgressDialogUtil

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var progressDialog: AlertDialog? = null
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createViewBinding(inflater, container)
        progressDialog = ProgressDialogUtil.setProgressDialog(requireContext()).create()
        return binding.root
    }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    fun showDialog(show: Boolean) {
        if (show)
            progressDialog?.show()
        else
            progressDialog?.dismiss()
    }

    protected fun navigateFragment(id: Int) {
        Navigation.findNavController(requireView())
            .navigate(id)
    }

    protected fun navigateFragment(id: Int, bundle: Bundle) {
        Navigation.findNavController(requireView())
            .navigate(id, bundle)
    }

    fun backPress() {
        val backPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallback
        )
    }
}