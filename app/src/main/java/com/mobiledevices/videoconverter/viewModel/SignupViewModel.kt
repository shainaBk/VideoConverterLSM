package com.mobiledevices.videoconverter.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.validation.Validator
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    fun signUpUser(pseudo: String, email: String,password:String,onSuccess: (User?) -> Unit, onError: (Exception?) -> Unit){
        viewModelScope.launch {
            try {
                val CurrentUser = FirestoreRepository.addUser(User(id = pseudo.lowercase(),mail = email, password = password))
                Log.i("InfoUserAdd","${CurrentUser.toString()}")
            }catch (e: Exception){
                onError(e)
                Log.e("ErrorSignUp","error on sign up: ${e}")
            }
        }
    }

    /**Validator Part*/
    fun checkPseudoAndSignUp(pseudo: String, email: String, password: String, repeatPassword: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            if (!Validator.isValidLogin(pseudo)) {
                onResult(false, "Pseudo non valide ou déjà utilisé")
            } else if (!Validator.isValidEmail(email)) {
                onResult(false, "Adresse email invalide (règle: name@truc.chose)")
            } else if (!Validator.isValidPassword(password, repeatPassword)) {
                onResult(false, "Mot de passe invalide ou ne correspond pas (règle: min 6 caractères)")

            } else {
                signUpUser(pseudo, email, password, ::onUserSignUpSuccess, ::onUserSignUpError)
                onResult(true, "")
            }
        }
    }
    private fun onUserSignUpSuccess(user: User?) {
        Log.i("SuccessSignUp","Success on sign up:${user.toString()}")
    }

    private fun onUserSignUpError(exception: Exception?) {
        Log.i("FailsSignUp","Fails on sign up")
    }

}