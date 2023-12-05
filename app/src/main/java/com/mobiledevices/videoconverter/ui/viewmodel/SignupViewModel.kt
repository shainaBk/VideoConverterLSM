package com.mobiledevices.videoconverter.Ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.Dao.FirestoreRepository
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    fun signUpUser(email: String,password:String,onSuccess: (User?) -> Unit, onError: (Exception?) -> Unit){
        viewModelScope.launch {
            try {
                val CurrentUser = FirestoreRepository.addUser(User(mail = email, password = password))
                Log.i("InfoUserAdd","${CurrentUser.toString()}")
            }catch (e: Exception){
                onError(e)
                Log.e("ErrorSignUp","error on sign up: ${e}")
            }
        }
    }
}