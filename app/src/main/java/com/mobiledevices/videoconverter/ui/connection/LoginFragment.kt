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
import com.mobiledevices.videoconverter.databinding.FragmentLoginBinding
import com.mobiledevices.videoconverter.model.User
import com.mobiledevices.videoconverter.viewModel.LoginViewModel

class LoginFragment : Fragment() {
    companion object {
        private const val TAG = "LoginFragment"
    }

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

            loginViewModel.checkPseudoPasswordLogIn(
                pseudo,
                password,
                ::onUserLogInSuccess
            ) { isValid, errorMessage ->
                if (isValid) {
                    Log.d(TAG, "Login success")
                    findNavController().navigate(R.id.action_loginFragment_to_applicationActivity)
                } else {
                    when {
                        errorMessage.contains("Pseudo") -> binding.tiEditUsername.error =
                            errorMessage

                        errorMessage.contains("Mot de passe") -> binding.tiEditPassword.error =
                            errorMessage
                    }
                }
            }
        }
        return binding.root
    }

    /**
     * onUserLogInSuccess: Gère le succès de la connexion de l'utilisateur.
     * @param user L'objet User représentant l'utilisateur connecté.
     */
    private fun onUserLogInSuccess(user: User) {
        UserManager.logIn(user)
    }

    /**
     * onDestroyView: Nettoie les ressources liées à la vue lorsque la vue du fragment est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}