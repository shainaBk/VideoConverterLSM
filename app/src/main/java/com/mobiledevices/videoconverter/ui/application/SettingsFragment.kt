package com.mobiledevices.videoconverter.Ui.application

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mobiledevices.videoconverter.databinding.FragmentSettingsBinding
import com.mobiledevices.videoconverter.Ui.connection.ConnectionActivity
import com.mobiledevices.videoconverter.viewModel.MusicViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MusicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.btnLogout.setOnClickListener {
            activity?.finish()
            val intent = Intent(activity, ConnectionActivity::class.java)
            startActivity(intent)
        }

        binding.btnClearMusic.setOnClickListener {
            viewModel.clearMusicList()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}