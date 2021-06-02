package com.example.cryptotracker.presentation.base

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cryptotracker.presentation.util.InternetControlUtil

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _bottomNavigationVisibility = MutableLiveData<Int>()
    private val _topBarVisibility = MutableLiveData<Int>()
    private var _networkConnection: InternetControlUtil
    val bottomNavigationVisibility: LiveData<Int>
        get() = _bottomNavigationVisibility
    val topBarVisibility: LiveData<Int>
        get() = _topBarVisibility
    val networkConnection: LiveData<Boolean>
        get() = _networkConnection

    init {
        showBottomNav()
        _networkConnection = InternetControlUtil(application)
    }

    fun showBottomNav() {
        _bottomNavigationVisibility.postValue(View.VISIBLE)
    }

    fun hideBottomNav() {
        _bottomNavigationVisibility.postValue(View.GONE)
    }

    fun showTopBar() {
        _topBarVisibility.postValue(View.VISIBLE)
    }

    fun hideTopBar() {
        _topBarVisibility.postValue(View.GONE)
    }
}