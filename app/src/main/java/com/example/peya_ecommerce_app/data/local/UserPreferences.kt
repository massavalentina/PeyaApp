package com.example.peya_ecommerce_app.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    private val Context.userDataStore by preferencesDataStore(name = "user_preferences")

    companion object {
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    val isLoggedIn: Flow<Boolean> = context.userDataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    val userEmail: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY]
    }

    val userId: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }

    suspend fun saveUser(email: String, userId: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = userId
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    suspend fun clearUser() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}