package com.example.cryptotracker.data.network.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import java.lang.reflect.Type

fun <T> ResponseBody.toModel(classOfT : Class<T>) : T{
    return Gson().fromJson(this.string(), classOfT)
}
