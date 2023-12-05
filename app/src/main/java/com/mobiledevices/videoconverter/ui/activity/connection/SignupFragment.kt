
package com.mobiledevices.videoconverter.ui.activities.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.Ui.viewModel.SignupViewModel
import com.mobiledevices.videoconverter.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)

        binding.ibBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSignup.setOnClickListener {
            signUpUser()
            findNavController().navigate(R.id.action_signupFragment_to_applicationActivity)
        }
        return binding.root
    }

    //TODO: champs validation mail mdp
    private fun signUpUser(){
        val email = binding.tiEditMail.text.toString()
        val password = binding.tiEditPassword.text.toString()
        signupViewModel.signUpUser(email,password,::onUserSignUpSuccess,::onUserSignUpError)
    }
    private fun onUserSignUpSuccess(user: User?) {
        Log.i("SuccessSignUp","Success on sign up:${user.toString()}")
    }

    private fun onUserSignUpError(exception: Exception?) {
        Log.i("FailsSignUp","Fails on sign up")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}