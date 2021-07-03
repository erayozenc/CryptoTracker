package com.example.cryptotracker.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.cryptotracker.data.local.LocalConstant.COIN_DATABASE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceStorage {

    private val dataStore: DataStore<Preferences> =
        context.createDataStore(name= COIN_DATABASE_NAME)


    private object PreferencesKey {
        val AUTH_TOKEN = preferencesKey<String>("auth_token")
    }

    override val authToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.AUTH_TOKEN]
        }


}