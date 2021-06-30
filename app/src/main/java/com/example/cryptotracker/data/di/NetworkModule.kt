package com.example.cryptotracker.data.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.cryptotracker.data.local.datastore.AppPreferences
import com.example.cryptotracker.data.mapper.CoinMapper
import com.example.cryptotracker.data.mapper.DetailedCoinMapper
import com.example.cryptotracker.data.mapper.CoinMarketChartMapper
import com.example.cryptotracker.data.network.service.ApiService
import com.example.cryptotracker.data.network.NetworkConstant
import com.example.cryptotracker.data.network.datasource.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideGsonBuilder() : Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson : Gson,
        @ApplicationContext context: Context,
        appPreferences: AppPreferences) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(NetworkConstant.BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor { chain ->
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Accept","application/json")
                        it.addHeader("X-appAuth_Key", NetworkConstant.X_APPAUTH_KEY)
                    }.build())
                }.also {client ->
                    if (BuildConfig.DEBUG){
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
            ).addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit.Builder
    ) = retrofit
        .build().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideCoinMarketsRemoteDataSource(
        apiService: ApiService
    ) = CoinMarketsRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideCoinListRemoteDataSource(
        apiService: ApiService
    ) = CoinListRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideCoinRemoteDataSource(
        apiService: ApiService
    ) = CoinRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideDetailedCoinMapper() = DetailedCoinMapper()

    @Provides
    @Singleton
    fun provideCoinDetailRemoteDataSource(
            apiService: ApiService
    ) = CoinDetailRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideCoinMarketChartMapper()= CoinMarketChartMapper()

    @Provides
    @Singleton
    fun provideTrendingCoinsRemoteDataSource(
            apiService: ApiService
    ) = TrendingCoinsRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideCoinMapper()= CoinMapper()

    @Provides
    @Singleton
    fun provideTopTenCoinsRemoteDataSource(
        apiService: ApiService
    ) = TopTenCoinsRemoteDataSource(apiService)

}