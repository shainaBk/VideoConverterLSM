package com.mobiledevices.videoconverter.Core.Utils
import java.security.MessageDigest
object PasswordUtils {
    /**
     * Hache un mot de passe en utilisant l'algorithme SHA-256.
     * @param password Le mot de passe à hacher.
     * @return La représentation hachée du mot de passe.
     */
    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }
}