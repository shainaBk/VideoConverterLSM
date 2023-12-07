package com.mobiledevices.videoconverter.Core.validation

import android.util.Log
import com.mobiledevices.videoconverter.Core.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Core.Utils.PasswordUtils

class Validator {
    companion object {
        /**SIGNUP PART**/
        fun isValidEmailSignUp(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            return email.matches(emailRegex)
        }

        fun isValidPasswordCreate(password: String, repeatPassword: String): Boolean {
            if (password != repeatPassword) return false // Les mots de passe ne correspondent pas
            val passwordRegex = "^.{6,}$".toRegex()
            return password.matches(passwordRegex)
        }

        suspend fun isValidPseudoSignUp(pseudo: String): Boolean {
            if (pseudo.length < 3 || pseudo.length > 15) {
                return false // Longueur du pseudo non valide
            }
            val pseudoBd = FirestoreRepository.getUser(pseudo.lowercase())?.id
            return !pseudo.lowercase().equals(pseudoBd)
        }

        /**LOGINPART**/
        suspend fun isValidPseudoLogIn(pseudo: String): Boolean{
            val pseudoBd = FirestoreRepository.getUser(pseudo.lowercase())?.id
            return pseudo.lowercase().equals(pseudoBd)
        }
        suspend fun isValidPasswordLogIn(pseudo: String,password: String): Boolean{
            val pseudoPassword = FirestoreRepository.getUser(pseudo.lowercase())?.password
            val tappedPasswordHash = PasswordUtils.hashPassword(password)
            return tappedPasswordHash.equals(pseudoPassword)
        }

    }
}