package com.mobiledevices.videoconverter.Model

import kotlinx.serialization.Serializable


data class User(var id: String = "", val mail: String = "", val password: String= "", val librarie: List<Music> = listOf()){
    /**
     * Ajoute une musique à la bibliothèque de l'utilisateur.
     * @param music La musique à ajouter.
     * @return Une nouvelle instance de User avec la musique ajoutée à la bibliothèque.
     */
    fun addMusic(music: Music): User {
        val updatedLibrary = librarie + music
        return this.copy(librarie = updatedLibrary)
    }
    /**
     * Ajoute une musique à la bibliothèque de l'utilisateur.
     * @param music La musique à ajouter.
     * @return Une nouvelle instance de User avec la musique ajoutée à la bibliothèque.
     */
    fun changePassword(newPassword: String): User {
        return this.copy(password = newPassword)
    }
    override fun toString(): String {
        return  "User mail: {$mail}";
    }

}
