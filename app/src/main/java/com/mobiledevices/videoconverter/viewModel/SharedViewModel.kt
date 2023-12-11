package com.mobiledevices.videoconverter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobiledevices.videoconverter.core.dao.FirestoreRepository
import com.mobiledevices.videoconverter.model.Music
import com.mobiledevices.videoconverter.model.User
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser

    /**
     * loginUser: Enregistre l'utilisateur actuellement connecté dans le ViewModel.
     * @param user L'instance de l'utilisateur connecté.
     */
    fun loginUser(user: User) {
        _currentUser.value = user
    }

    /**
     * logoutUser: Efface les données de l'utilisateur connecté, marquant ainsi la déconnexion.
     */
    fun logoutUser() {
        _currentUser.value = null
    }

    /**
     * changePassword: Met à jour le mot de passe de l'utilisateur connecté.
     * @param password Le nouveau mot de passe (déjà haché).
     */
    fun changePassword(password: String) {
        _currentUser.value = currentUser.value?.changePassword(password)
    }

    /**
     * addMusicToLibrary: Ajoute une musique à la bibliothèque de l'utilisateur et met à jour la base de données.
     * @param music L'objet musique à ajouter à la bibliothèque de l'utilisateur.
     */
    fun addMusicToLibrary(music: Music) {
        val user = _currentUser.value
        user?.let {
            val updatedLibrary = user.librarie.toMutableList().apply {
                add(music)
            }
            _currentUser.value = user.copy(librarie = updatedLibrary)

            viewModelScope.launch {
                FirestoreRepository.addMusicToUser(user.id, music)
            }
        }
    }

    /**
     * removeMusicFromLibrary: Supprime une musique de la bibliothèque de l'utilisateur et met à jour la base de données.
     * @param music L'objet musique à supprimer de la bibliothèque de l'utilisateur.
     */
    fun removeMusicFromLibrary(music: Music) {
        val user = _currentUser.value
        user?.let {
            val updatedLibrary = user.librarie.filter { it.videoId != music.videoId }

            _currentUser.value = user.copy(librarie = updatedLibrary)

            viewModelScope.launch {
                FirestoreRepository.removeMusicFromUser(user.id, music)
            }
        }
    }
}
