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

    // Extensión para obtener la instancia de DataStore
    private val Context.userDataStore by preferencesDataStore(name = "user_preferences")
    // Claves para guardar los datos del usuario
    companion object {
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    // Obtener el estado de sesión almacenado
    val isLoggedIn: Flow<Boolean> = context.userDataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    // Obtener el email del usuario
    val userEmail: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[USER_EMAIL_KEY]
    }

    // Guardar los datos del usuario autenticado
    suspend fun saveUser(email: String, userId: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_ID_KEY] = userId
            preferences[IS_LOGGED_IN_KEY] = true
            Log.d("UserPreferences", "Usuario guardado: Email: $email, ID: $userId, isLoggedIn: true")
        }
    }

    // Limpiar los datos del usuario (Logout)
    suspend fun clearUser() {
        context.userDataStore.edit { preferences ->
            preferences.clear()
            Log.d("UserPreferences", "Datos del usuario eliminados")
        }
    }
}