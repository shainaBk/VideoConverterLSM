package com.mobiledevices.videoconverter.Model


data class User(val id: String = "", val mail: String = "", val password: String= "", val librarie: List<Music> = listOf()){
    fun addMusic(music: Music): User {
        val updatedLibrary = librarie + music
        return this.copy(librarie = updatedLibrary)
    }
    fun changePassword(newPassword: String): User {
        return this.copy(password = newPassword)
    }
    override fun toString(): String {
        return  "User mail: {$mail}";
    }

}
