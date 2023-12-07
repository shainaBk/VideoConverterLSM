package com.mobiledevices.videoconverter.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiledevices.videoconverter.Model.User

class SharedViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    fun loginUser(user: User){
        _currentUser.value = user
    }

    fun logoutUser(){
        _currentUser.value = null
    }
    fun changePassword(password:String){
        _currentUser.value = currentUser.value?.changePassword(password)
    }

}
