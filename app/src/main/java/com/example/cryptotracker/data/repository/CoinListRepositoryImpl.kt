package com.example.cryptotracker.data.repository
import com.example.cryptotracker.data.ApiResult
import com.example.cryptotracker.data.local.datasource.CoinListDataSource
import com.example.cryptotracker.data.mapper.CoinEntityMapper
import com.example.cryptotracker.data.mapper.CoinEntityToDomainMapper
import com.example.cryptotracker.data.mapper.CoinMapper
import com.example.cryptotracker.data.network.datasource.CoinListRemoteDataSource
import com.example.cryptotracker.domain.Resource
import com.example.cryptotracker.domain.model.CoinDomainModel
import com.example.cryptotracker.domain.repository.CoinListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinListRepositoryImpl @Inject constructor(
        private val remoteDataSource: CoinListRemoteDataSource,
        private val dataSource: CoinListDataSource,
        private val mapper: CoinMapper,
        private val entityMapper: CoinEntityMapper,
        private val entityToDomainMapper: CoinEntityToDomainMapper
) : CoinListRepository {

    override suspend fun getCoinList() : Flow<Resource<List<CoinDomainModel>>> = flow {
        when(val data = remoteDataSource.getCoinList()) {
            is ApiResult.Success -> {
                dataSource.insert(entityMapper.mapList(data.value))
                emit(Resource.Success(mapper.mapList(data.value)))
            }

            is ApiResult.Failure -> {
                emit(Resource.Error(data.errorBody?.toString() ?: "An error occured!", null))
            }
        }
    }

    override fun getCoinListFromDatabase(query: String) =
            dataSource.getCoins(query).map { entityToDomainMapper.mapList(it) }

    override fun getCoinFromDatabase(id: String) =
            dataSource.getCoin(id).map { entityToDomainMapper.map(it) }
}