package com.example.cryptotracker.data

import com.example.cryptotracker.domain.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading)
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            when(val apiResult = createCall().first()) {
                is ApiResult.Success -> {
                    saveCallResult(apiResult.value)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResult.Failure -> {
                    onFetchFailed()
                    emit(Resource.Error(apiResult.errorBody.toString(), null))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResult<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}