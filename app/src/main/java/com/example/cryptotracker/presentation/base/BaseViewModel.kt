package com.example.cryptotracker.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _snackbar = MutableLiveData<String>()
    val snackbar : LiveData<String> = _snackbar

    protected val _toast = MutableLiveData<String>()
    val toast : LiveData<String> = _toast

    protected val _progressBar = MutableLiveData<Boolean>()
    val progressBar : LiveData<Boolean> = _progressBar


}