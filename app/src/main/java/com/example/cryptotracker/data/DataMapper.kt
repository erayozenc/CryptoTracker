package com.example.cryptotracker.data

interface DataMapper<DataModel, DomainModel> {

    fun map(dto: DataModel): DomainModel

    fun mapList(dataList : List<DataModel>) : List<DomainModel>
}