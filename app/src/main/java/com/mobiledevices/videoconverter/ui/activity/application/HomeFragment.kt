package com.mobiledevices.videoconverter.ui.activity.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.adapter.MusicHomeAdapter
import com.mobiledevices.videoconverter.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val musicList = mutableListOf<Music>()
        val adapter = MusicHomeAdapter(musicList)

        binding.rvDownload.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        binding.newSearch.setOnClickListener {
            openSearchDialog(adapter)
        }

        updateNoResultImage(musicList)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openSearchDialog(adapter: MusicHomeAdapter) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_search_music, null)
        val editText = dialogView.findViewById<EditText>(R.id.et_search_music)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.search_music))
            .setView(dialogView)
            .setPositiveButton("Search") { _, _ ->
                val musicName = editText.text.toString()

                adapter.addMusic(Music("", "", "", musicName, "Artist Name"))
                updateNoResultImage(adapter.musicList)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateNoResultImage(list: List<Music>) {
        binding.ivNoResult.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}