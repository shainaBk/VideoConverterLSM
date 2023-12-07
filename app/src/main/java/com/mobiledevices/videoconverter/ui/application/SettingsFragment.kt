package com.mobiledevices.videoconverter.Ui.application

import com.mobiledevices.videoconverter.Ui.connection.ConnectionActivity
import com.mobiledevices.videoconverter.databinding.FragmentSettingsBinding
import com.mobiledevices.videoconverter.viewModel.MusicViewModel
import com.mobiledevices.videoconverter.viewModel.SettingsViewModel
import com.mobiledevices.videoconverter.viewModel.SharedViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mobiledevices.videoconverter.R


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
        Log.i("SomeFragment3","in set ${sharedViewModel.currentUser.value?.id}")

        binding.btnChangePassword.setOnClickListener{
            val password = binding.tiEditNewPassword.text.toString()
            val passwordConf = binding.tiEditNewConfPassword.text.toString()
            val userId = sharedViewModel.currentUser.value?.id
            Log.i("sessionVar2","${sharedViewModel.currentUser.value?.id}")
            if (userId != null) {
                settingViewModel.checkNewPasswordSettings(password,passwordConf,userId) { isValid, errorMessage ->
                    if (isValid) {

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
            Log.i("SomeFragment","${sharedViewModel.currentUser.value?.id}")
            //sharedViewModel.logoutUser()
            //Log.i("sessionVar","${sharedViewModel.currentUser.value?.id}")
            /*activity?.finish()
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)*/
        }

        binding.btnClearMusic.setOnClickListener {
            musicViewModel.clearMusicList()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}