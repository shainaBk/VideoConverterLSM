package com.mobiledevices.videoconverter.core.utils

import com.mobiledevices.videoconverter.model.User

object UserManager {
    var currentUser: User? = null

    fun logOut() {
        currentUser = null
    }

    fun logIn(user: User) {
        currentUser = user
    }
}