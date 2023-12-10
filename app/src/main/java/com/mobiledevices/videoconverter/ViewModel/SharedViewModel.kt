package com.mobiledevices.videoconverter.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.Core.Dao.FirestoreRepository
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.Model.User
import kotlinx.coroutines.launch

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
    fun addMusicToLibrary(music:Music){
        val user = _currentUser.value
        user?.let {
            val updatedLibrary = user.librarie.toMutableList().apply {
                add(music)
            }
            _currentUser.value = user.copy(librarie = updatedLibrary)

            viewModelScope.launch{
                FirestoreRepository.addMusicToUser(user.id,music)
            }
        }
    }
    fun removeMusicFromLibrary(music: Music) {
        val user = _currentUser.value
        user?.let {
            val updatedLibrary = user.librarie.filter { it.videoId != music.videoId }

            _currentUser.value = user.copy(librarie = updatedLibrary)

            viewModelScope.launch{
                FirestoreRepository.removeMusicFromUser(user.id, music)
            }
        }
    }



}
