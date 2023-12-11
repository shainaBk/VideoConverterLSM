package com.mobiledevices.videoconverter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.core.dao.FirestoreRepository
import com.mobiledevices.videoconverter.core.utils.PasswordUtils
import com.mobiledevices.videoconverter.core.validation.Validator
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    /**
     * checkNewPasswordSettings: Valide et met à jour le nouveau mot de passe de l'utilisateur.
     * @param password Le nouveau mot de passe proposé par l'utilisateur.
     * @param repeatPassword La répétition du nouveau mot de passe pour la confirmation.
     * @param userId L'identifiant de l'utilisateur pour lequel le mot de passe doit être mis à jour.
     * @param onResult Fonction de callback appelée avec le résultat de la mise à jour (succès ou échec) et un message d'erreur ou de succès.
     */
    fun checkNewPasswordSettings(
        password: String,
        repeatPassword: String,
        userId: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            if (!Validator.isValidPasswordCreate(password, repeatPassword)) {
                onResult(
                    false,
                    "Mot de passe invalide ou ne correspond pas (règle: min 6 caractères)"
                )
            } else {
                val hashedPassword = PasswordUtils.hashPassword(password)
                FirestoreRepository.updatePasswordUser(userId, hashedPassword)
                onResult(true, "")
            }
        }
    }
}