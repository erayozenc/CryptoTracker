package com.example.cryptotracker.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceStorage {

    val authToken : Flow<String?>

    //suspend fun saveAuthToken(authToken: String)

    //suspend fun clearPreferenceStorage()
}