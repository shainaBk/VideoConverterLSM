
package com.mobiledevices.videoconverter.Ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.mobiledevices.videoconverter.Model.User
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.viewModel.SignupViewModel
import com.mobiledevices.videoconverter.databinding.FragmentSignupBinding
import com.mobiledevices.videoconverter.validation.Validator
import com.mobiledevices.videoconverter.viewModel.SharedViewModel

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!


    private val signupViewModel: SignupViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        sharedViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            Log.i("SomeFragment", "Current user (in sign): ${user?.id}")
        }
        binding.ibBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSignup.setOnClickListener {
            val email = binding.tiEditMail.text.toString()
            val password = binding.tiEditPassword.text.toString()
            val repeatPassword=binding.tiEditConfPassword.text.toString()
            val pseudo = binding.tiEditUsername.text.toString()

            signupViewModel.checkPseudoAndSignUp(pseudo, email, password, repeatPassword,::onUserSignUpSuccess){ isValid, errorMessage ->
                if (isValid) {
                    findNavController().navigate(R.id.action_signupFragment_to_applicationActivity)
                } else {
                    handleValidationErrors(errorMessage)
                }
            }
        }
        return binding.root
    }
    private fun onUserSignUpSuccess(user: User) {
        sharedViewModel.loginUser(user)
    }
    private fun handleValidationErrors(errorMessage: String) {
        when {
            errorMessage.contains("Pseudo") -> binding.tiEditUsername.error = errorMessage
            errorMessage.contains("email") -> binding.tiEditMail.error = errorMessage
            errorMessage.contains("Mot de passe") -> {
                binding.tiEditPassword.error = errorMessage
                binding.tiEditConfPassword.error = errorMessage
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}