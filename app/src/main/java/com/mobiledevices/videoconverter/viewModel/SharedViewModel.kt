package com.mobiledevices.videoconverter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiledevices.videoconverter.Dao.FirestoreRepository
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

    suspend fun sharedViewModel(userId: String){
        _currentUser.value = FirestoreRepository.getUser(userId)
    }
}
