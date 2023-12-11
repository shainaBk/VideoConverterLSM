package com.mobiledevices.videoconverter.ui.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.core.utils.UserManager
import com.mobiledevices.videoconverter.databinding.FragmentSignupBinding
import com.mobiledevices.videoconverter.model.User
import com.mobiledevices.videoconverter.viewModel.SignupViewModel

class SignupFragment : Fragment() {
    companion object {
        private const val TAG = "SignupFragment"
    }

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
            val email = binding.tiEditMail.text.toString()
            val password = binding.tiEditPassword.text.toString()
            val repeatPassword = binding.tiEditConfPassword.text.toString()
            val pseudo = binding.tiEditUsername.text.toString()

            signupViewModel.checkPseudoAndSignUp(
                pseudo,
                email,
                password,
                repeatPassword,
                ::onUserSignUpSuccess
            ) { isValid, errorMessage ->
                if (isValid) {
                    Log.i(TAG, "Signup success")
                    findNavController().navigate(R.id.action_signupFragment_to_applicationActivity)
                } else {
                    handleValidationErrors(errorMessage)
                }
            }
        }
        return binding.root
    }

    /**
     * onUserSignUpSuccess: Gère le succès de l'inscription de l'utilisateur.
     * @param user L'objet User représentant l'utilisateur inscrit.
     */
    private fun onUserSignUpSuccess(user: User) {
        UserManager.logIn(user)
    }

    /**
     * handleValidationErrors: Gère les erreurs de validation lors de l'inscription.
     * @param errorMessage Le message d'erreur de validation.
     */
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