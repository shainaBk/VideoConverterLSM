package com.mobiledevices.videoconverter.Model


data class User(val id: String? = null, val mail: String = "", val password: String= "", val librarie: List<Music> = listOf()){
    override fun toString(): String {
        return  "User mail: {$mail}";
    }
}
