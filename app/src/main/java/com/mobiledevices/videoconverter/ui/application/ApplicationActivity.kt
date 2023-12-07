package com.mobiledevices.videoconverter.Ui.application

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.Service.MusicService
import com.mobiledevices.videoconverter.Utils.UserManager
import com.mobiledevices.videoconverter.databinding.ActivityApplicationBinding
import com.mobiledevices.videoconverter.viewModel.SharedViewModel
import kotlinx.coroutines.launch

class ApplicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApplicationBinding
    private val sharedViewModel: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.fl_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.setupWithNavController(navController)

        val user = UserManager.currentUser
        if (user != null) {
            sharedViewModel.loginUser(user)
        }
    }

}
