package com.mobiledevices.videoconverter.ui.connection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

        val binding = ActivityConnectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the CircularProgressIndicator and overlay view by their IDs
        val progressIndicator = binding.piChecking
        val overlayView = binding.viewMask

        // Show the progress indicator and overlay view
        progressIndicator.visibility = View.VISIBLE
        overlayView.visibility = View.VISIBLE

        if (SessionManager.isLoggedIn(this)) {
            val pseudo = SessionManager.getUsername(this)!!
            val password = SessionManager.getPassword(this)!!
            loginViewModel.checkPseudoPasswordLogIn(
                pseudo,
                password,
                ::onUserLogInSuccess
            ) { isValid, _ ->
                progressIndicator.visibility = View.GONE
                overlayView.visibility = View.GONE

                if (isValid) {
                    Log.i("ConnectionActivity", "User is logged in")
                    val intent = Intent(this, ApplicationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            // Hide the progress indicator and overlay view
            progressIndicator.visibility = View.GONE
            overlayView.visibility = View.GONE
        }

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