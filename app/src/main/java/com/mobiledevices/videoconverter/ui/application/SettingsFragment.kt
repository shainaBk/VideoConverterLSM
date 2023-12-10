package com.mobiledevices.videoconverter.Ui.application

import com.mobiledevices.videoconverter.Ui.connection.ConnectionActivity
import com.mobiledevices.videoconverter.databinding.FragmentSettingsBinding
import com.mobiledevices.videoconverter.ViewModel.MusicViewModel
import com.mobiledevices.videoconverter.ViewModel.SettingsViewModel
import com.mobiledevices.videoconverter.ViewModel.SharedViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mobiledevices.videoconverter.Core.Utils.PasswordUtils
import com.mobiledevices.videoconverter.Core.Utils.UserManager


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val musicViewModel: MusicViewModel by activityViewModels()
    private val settingViewModel: SettingsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.btnChangePassword.setOnClickListener{
            val password = binding.tiEditNewPassword.text.toString()
            val passwordConf = binding.tiEditNewConfPassword.text.toString()
            val userId = sharedViewModel.currentUser.value?.id
            if (userId != null) {
                settingViewModel.checkNewPasswordSettings(password,passwordConf,userId) { isValid, errorMessage ->
                    if (isValid) {
                        sharedViewModel.changePassword(PasswordUtils.hashPassword(password))
                        Toast.makeText(context, "Mot de passe mit à jours !", Toast.LENGTH_SHORT).show()
                    } else {
                        when{
                            errorMessage.contains("Mot de passe") -> {
                                binding.tiEditNewPassword.error = errorMessage
                                binding.tiEditNewConfPassword.error = errorMessage
                            }

                        }
                    }
                }
            }

        }

        binding.btnLogout.setOnClickListener {
            //var de session
            sharedViewModel.logoutUser()
            UserManager.logOut()
            Log.i("LogOutSucess","Log out sucessful !!")
            activity?.finish()
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
        }

        binding.btnClearMusic.setOnClickListener {
            musicViewModel.clearMusicList()
        }

        return binding.root
    }

    /**
     * onDestroyView: Nettoie et libère le binding lors de la destruction de la vue du fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}