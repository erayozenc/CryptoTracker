package com.example.cryptotracker.data.network.datasource

import com.example.cryptotracker.data.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRemoteDataSource {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : ApiResult<T> {
        return withContext(Dispatchers.IO){

            try {
                ApiResult.Success(apiCall.invoke())
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        ApiResult.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )
                    }
                    else ->{
                        println(throwable.localizedMessage)
                        ApiResult.Failure(true, null, null)
                    }
                }
            }
        }
    }
}