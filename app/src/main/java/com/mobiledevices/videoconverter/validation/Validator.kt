package com.mobiledevices.videoconverter.validation

import android.util.Log
import com.mobiledevices.videoconverter.Dao.FirestoreRepository

class Validator {
    companion object {
        /**SIGNUP PART**/
        fun isValidEmail(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            return email.matches(emailRegex)
        }

        fun isValidPassword(password: String, repeatPassword: String): Boolean {
            if (password != repeatPassword) return false // Les mots de passe ne correspondent pas
            val passwordRegex = "^.{6,}$".toRegex()
            return password.matches(passwordRegex)
        }

        suspend fun isValidLogin(pseudo: String): Boolean {
            if (pseudo.length < 3 || pseudo.length > 15) {
                return false // Longueur du pseudo non valide
            }
            val pseudoBd = FirestoreRepository.getUser(pseudo.lowercase())?.id
            return !pseudo.lowercase().equals(pseudoBd)
        }

        /**LOGINPART**/
    }
}