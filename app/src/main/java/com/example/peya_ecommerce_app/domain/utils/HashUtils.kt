package com.example.peya_ecommerce_app.domain.utils

import java.security.MessageDigest

object HashUtils {

    /**
     * Convierte una contraseña en texto plano a su hash SHA-256.
     *
     * @param password Contraseña a encriptar.
     * @return El hash en formato hexadecimal.
     */
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray() // Convierte la contraseña en bytes.
        val md = MessageDigest.getInstance("SHA-256") // Obtiene la instancia de SHA-256.
        val digest = md.digest(bytes) // Genera el hash.
        return digest.joinToString("") { "%02x".format(it) } // Convierte a hexadecimal.
    }
}