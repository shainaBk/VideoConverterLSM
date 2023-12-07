package com.mobiledevices.videoconverter.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Utils.PasswordUtils
import com.mobiledevices.videoconverter.validation.Validator
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    /**Validator Part*/
    fun checkPseudoAndSignUp(pseudo: String, email: String, password: String, repeatPassword: String,onSuccess: (User) -> Unit, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (!Validator.isValidPseudoSignUp(pseudo)) {
                onResult(false, "Pseudo non valide ou déjà utilisé")
            } else if (!Validator.isValidEmailSignUp(email)) {
                onResult(false, "Adresse email invalide (règle: name@truc.chose)")
            } else if (!Validator.isValidPasswordCreate(password, repeatPassword)) {
                onResult(false, "Mot de passe invalide ou ne correspond pas (règle: min 6 caractères)")

            } else {
                try {
                    val hashedPassword = PasswordUtils.hashPassword(password)
                    val newUser = User(id = pseudo.lowercase(), mail = email, password = hashedPassword)
                    val CurrentUser = FirestoreRepository.addUser(newUser)
                    if (CurrentUser != null) {
                        onSuccess(CurrentUser)
                    }
                }catch (e: Exception){
                    onUserSignUpError(e)
                    Log.e("ErrorSignUp","error on sign up: ${e}")
                }
                onResult(true, "")

            }
        }
    }

    private fun onUserSignUpError(exception: Exception?) {
        Log.i("FailsSignUp","Fails on sign up")
    }

}