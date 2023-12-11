package com.mobiledevices.videoconverter.core.validation

import com.mobiledevices.videoconverter.core.dao.FirestoreRepository
import com.mobiledevices.videoconverter.core.utils.PasswordUtils

/**
 * La classe Validator fournit des méthodes de validation pour différents types d'entrées utilisateur,
 * notamment pour les inscriptions et les connexions.
 */
class Validator {
    companion object {
        //SIGNUP PART
        /**
         * Vérifie si l'email fourni est valide.
         * @param email L'email à valider.
         * @return True si l'email est valide, sinon False.
         */
        fun isValidEmailSignUp(email: String): Boolean {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
            return email.matches(emailRegex)
        }

        /**
         * Vérifie si le mot de passe et la confirmation du mot de passe sont valides et correspondent.
         * @param password Le mot de passe à valider.
         * @param repeatPassword La confirmation du mot de passe.
         * @return True si les critères sont respectés, sinon False.
         */
        fun isValidPasswordCreate(password: String, repeatPassword: String): Boolean {
            if (password != repeatPassword) return false // Les mots de passe ne correspondent pas
            val passwordRegex = "^.{6,}$".toRegex()
            return password.matches(passwordRegex)
        }

        /**
         * Vérifie si le pseudo est valide pour l'inscription.
         * @param pseudo Le pseudo à valider.
         * @return True si le pseudo est valide et unique, sinon False.
         */
        suspend fun isValidPseudoSignUp(pseudo: String): Boolean {
            if (pseudo.length < 3 || pseudo.length > 15)
                return false // Longueur du pseudo non valide
            val pseudoBd = FirestoreRepository.getUser(pseudo.lowercase())?.id
            return pseudo.lowercase() != pseudoBd
        }

        //LOGIN PART
        /**
         * Vérifie si le pseudo existe pour la connexion.
         * @param pseudo Le pseudo à valider.
         * @return True si le pseudo existe, sinon False.
         */
        suspend fun isValidPseudoLogIn(pseudo: String): Boolean {
            val pseudoBd = FirestoreRepository.getUser(pseudo.lowercase())?.id
            return pseudo.lowercase() == pseudoBd
        }

        /**
         * Vérifie si le mot de passe correspond au pseudo pour la connexion.
         * @param pseudo Le pseudo de l'utilisateur.
         * @param password Le mot de passe à valider.
         * @return True si le mot de passe correspond, sinon False.
         */
        suspend fun isValidPasswordLogIn(pseudo: String, password: String): Boolean {
            val pseudoPassword = FirestoreRepository.getUser(pseudo.lowercase())?.password
            val tappedPasswordHash = PasswordUtils.hashPassword(password)
            return tappedPasswordHash == pseudoPassword
        }
    }
}