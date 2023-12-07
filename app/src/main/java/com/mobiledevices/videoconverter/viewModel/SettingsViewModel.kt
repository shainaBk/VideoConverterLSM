package com.mobiledevices.videoconverter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Utils.PasswordUtils
import com.mobiledevices.videoconverter.validation.Validator
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    fun checkNewPasswordSettings(password: String,repeatPassword: String, userId:String, onResult: (Boolean, String) -> Unit){
        viewModelScope.launch {
            if (!Validator.isValidPasswordCreate(password, repeatPassword)) {
                onResult(false, "Mot de passe invalide ou ne correspond pas (règle: min 6 caractères)")
            } else {
                val hashedPassword = PasswordUtils.hashPassword(password)
                FirestoreRepository.updatePasswordUser(userId,hashedPassword)
                onResult(true, "")
            }
        }
    }
}