package com.mobiledevices.videoconverter.Utils

import com.mobiledevices.videoconverter.Model.User

object UserManager {
    var currentUser: User? = null

    fun logOut(){
        currentUser = null
    }

    fun logIn(user: User){
        currentUser = user
    }
}