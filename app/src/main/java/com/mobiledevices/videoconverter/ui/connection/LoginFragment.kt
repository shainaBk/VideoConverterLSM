package com.mobiledevices.videoconverter.Ui.connection

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
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.Ui.application.ApplicationActivity
import com.mobiledevices.videoconverter.Utils.UserManager
import com.mobiledevices.videoconverter.databinding.FragmentLoginBinding
import com.mobiledevices.videoconverter.viewModel.LoginViewModel
import com.mobiledevices.videoconverter.viewModel.SharedViewModel
import com.mobiledevices.videoconverter.viewModel.SignupViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.ibBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnLogin.setOnClickListener {
            val pseudo = binding.tiEditUsername.text.toString()
            val password = binding.tiEditPassword.text.toString()

            loginViewModel.checkPseudoPasswordLogIn(pseudo,password,::onUserLogInSuccess) { isValid, errorMessage ->
                if (isValid) {
                    Log.i("LogInSucess","Log in sucessful !!")
                    findNavController().navigate(R.id.action_loginFragment_to_applicationActivity)
                } else {
                    when{
                        errorMessage.contains("Pseudo") -> binding.tiEditUsername.error = errorMessage
                        errorMessage.contains("Mot de passe") -> binding.tiEditPassword.error = errorMessage
                    }
                }
            }
        }
        return binding.root
    }
    private fun onUserLogInSuccess(user: User) {
        UserManager.logIn(user)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}