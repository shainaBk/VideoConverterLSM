package com.mobiledevices.videoconverter.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.core.dao.FirestoreRepository
import com.mobiledevices.videoconverter.core.utils.PasswordUtils
import com.mobiledevices.videoconverter.core.validation.Validator
import com.mobiledevices.videoconverter.model.User
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    /**
     * checkPseudoAndSignUp: Vérifie la validité des informations de l'utilisateur et procède à l'inscription.
     * @param pseudo Le pseudonyme de l'utilisateur.
     * @param email L'adresse e-mail de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @param repeatPassword Le mot de passe répété pour vérification.
     * @param onSuccess Fonction à exécuter en cas de succès de l'inscription.
     * @param onResult Fonction callback pour indiquer le résultat de l'opération.
     */
    fun checkPseudoAndSignUp(
        pseudo: String,
        email: String,
        password: String,
        repeatPassword: String,
        onSuccess: (User) -> Unit,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            if (!Validator.isValidPseudoSignUp(pseudo)) {
                onResult(false, "Pseudo non valide ou déjà utilisé")
            } else if (!Validator.isValidEmailSignUp(email)) {
                onResult(false, "Adresse email invalide (règle: name@truc.chose)")
            } else if (!Validator.isValidPasswordCreate(password, repeatPassword)) {
                onResult(
                    false,
                    "Mot de passe invalide ou ne correspond pas (règle: min 6 caractères)"
                )

            } else {
                try {
                    val hashedPassword = PasswordUtils.hashPassword(password)
                    val newUser =
                        User(id = pseudo.lowercase(), mail = email, password = hashedPassword)
                    val CurrentUser = FirestoreRepository.addUser(newUser)
                    if (CurrentUser != null) {
                        onSuccess(CurrentUser)
                    }
                } catch (e: Exception) {
                    onUserSignUpError(e)
                    Log.e("ErrorSignUp", "error on sign up: ${e}")
                }
                onResult(true, "")

            }
        }
    }

    /**
     * onUserSignUpError: Gère les erreurs survenues lors de la tentative d'inscription de l'utilisateur.
     * @param exception L'exception survenue lors de la tentative d'inscription.
     */
    private fun onUserSignUpError(exception: Exception?) {
        Log.i("FailsSignUp", "Fails on sign up")
    }

}