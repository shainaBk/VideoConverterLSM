package com.mobiledevices.videoconverter.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Core.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.Core.validation.Validator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    /**
     * checkPseudoPasswordLogIn: Valide le pseudo et le mot de passe de l'utilisateur pour la connexion.
     * @param pseudo Le pseudo de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @param onSuccess Fonction de callback appelée en cas de succès avec l'utilisateur connecté.
     * @param onResult Fonction de callback appelée avec le résultat de la validation (succès ou échec) et un message d'erreur ou de succès.
     */
    fun checkPseudoPasswordLogIn(pseudo: String, password: String, onSuccess: (User) -> Unit, onResult: (Boolean, String) -> Unit){
        viewModelScope.launch {
            if (!Validator.isValidPseudoLogIn(pseudo)){
                onResult(false,"Pseudo non-existant")
            }else if(!Validator.isValidPasswordLogIn(pseudo,password)){
                onResult(false,"Mot de passe incorrecte")
            }else{
                val currentUser = FirestoreRepository.getUser(pseudo)
                if (currentUser != null) {
                    onSuccess(currentUser)
                }
                    onResult(true,"Erreur de connexion")

            }

        }
    }
}