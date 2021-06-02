package com.example.cryptotracker.presentation.base

interface ViewStateMapper<DomainModel, ViewState> {

    fun map(domainModel: DomainModel): ViewState

    fun mapList(domainList : List<DomainModel>) : List<ViewState>
}