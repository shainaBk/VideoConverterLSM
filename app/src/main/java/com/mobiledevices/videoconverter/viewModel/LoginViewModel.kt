package com.mobiledevices.videoconverter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.validation.Validator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    /**Validator Part*/
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