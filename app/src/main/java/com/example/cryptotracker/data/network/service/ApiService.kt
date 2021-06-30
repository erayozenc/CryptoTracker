package com.example.cryptotracker.data.network.service

import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @GET
    suspend fun getData(
        @Url url: String,
        @Header("Authorization") token: String? = null
    ) : ResponseBody

    @GET
    suspend fun getData(
            @Url url: String,
            @QueryMap queries:  Map<String, @JvmSuppressWildcards Any?>? = null,
            @Header("Authorization") token: String? = null
    ) : ResponseBody


    @FormUrlEncoded
    @POST
    suspend fun postData(
        @Url url: String,
        @FieldMap params: HashMap<String, Any?>? = null,
        @Header("Authorization") token: String? = null
    ) : ResponseBody

}