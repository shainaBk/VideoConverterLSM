package com.mobiledevices.videoconverter.ui.connection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.core.utils.SessionManager
import com.mobiledevices.videoconverter.core.utils.UserManager
import com.mobiledevices.videoconverter.databinding.ActivityConnectionBinding
import com.mobiledevices.videoconverter.model.User
import com.mobiledevices.videoconverter.ui.application.ApplicationActivity
import com.mobiledevices.videoconverter.viewModel.LoginViewModel

class ConnectionActivity : AppCompatActivity() {

    // Used to navigate between fragments
    private lateinit var navController: NavController

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (SessionManager.isLoggedIn(this)) {
            val pseudo = SessionManager.getUsername(this)!!
            val password = SessionManager.getPassword(this)!!
            loginViewModel.checkPseudoPasswordLogIn(
                pseudo,
                password,
                ::onUserLogInSuccess
            ) { isValid, _ ->
                if (isValid) {
                    Log.i("ConnectionActivity", "User is logged in")
                    val intent = Intent(this, ApplicationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        val binding = ActivityConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the navigation host fragment from this Activity
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
    }

    private fun onUserLogInSuccess(user: User) {
        UserManager.logIn(user)
    }
}