package com.mobiledevices.videoconverter.core.utils

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME: String = "UserSessionPref"
    private const val IS_LOGIN: String = "IsLoggedIn"
    private const val USERNAME: String = "Username"
    private const val PASSWORD: String = "Password"

    /**
     * Check login method wil check user login status.
     * If false it will redirect user to login page.
     * Else won't do anything.
     */
    fun createLoginSession(context: Context, username: String, password: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(USERNAME, username)
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(IS_LOGIN, false)
    }

    fun getUsername(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USERNAME, null)
    }

    fun getPassword(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PASSWORD, null)
    }

    fun logoutUser(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}