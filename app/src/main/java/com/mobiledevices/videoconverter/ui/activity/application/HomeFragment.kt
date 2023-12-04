package com.mobiledevices.videoconverter.ui.activity.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.adapter.MusicHomeAdapter
import com.mobiledevices.videoconverter.databinding.FragmentHomeBinding
import com.mobiledevices.videoconverter.viewModel.MusicViewModel

class HomeFragment : Fragment(), MusicHomeAdapter.OnMusicDownloadListener {
    // We use the view binding feature to avoid using findViewById()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // We use the view model to store the music list during the fragment lifecycle
    private val viewModel: MusicViewModel by viewModels()

    /**
     * Create the view and the recycler view adapter when the fragment view is created (rotation, ...)
     * @param inflater The layout inflater: XML to view
     * @param container The view group container: parent view
     * @param savedInstanceState The saved instance state: not used
     * @return The view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = MusicHomeAdapter(mutableListOf(), this) // Create the recycler view adapter
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set the recycler view adapter and layout manager
        binding.rvDownload.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        // Observe the music list to update the recycler view adapter
        viewModel.musicList.observe(viewLifecycleOwner, Observer { _ ->
            val nonDownloadedList = viewModel.getNonDownloadedMusic()
            adapter.updateMusicList(nonDownloadedList)
            updateNoResultImage(nonDownloadedList)
        })

        // Set the click listener on the search button to open the search dialog
        binding.newSearch.setOnClickListener { openSearchDialog() }

        return binding.root
    }

    /**
     * Destroy the view binding when the fragment view is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Mark a music as downloaded
     * @param music The music to mark as downloaded
     */
    override fun onMusicDownload(music: Music) {
        viewModel.markMusicAsDownloaded(music)
        println("Music downloaded: $music")
        println("Remaining music: ${viewModel.getNonDownloadedMusic()}")
    }

    /**
     * Open the search dialog to add a new music
     */
    private fun openSearchDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_search_music, null)
        val editText = dialogView.findViewById<EditText>(R.id.et_search_music)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.search_music))
            .setView(dialogView)
            .setPositiveButton("Search") { _, _ ->
                val musicName = editText.text.toString()
                val newMusic = Music(musicName, musicName, musicName, musicName, musicName)
                viewModel.addMusic(newMusic)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    /**
     * Update the no result image visibility
     * @param list The music list
     */
    private fun updateNoResultImage(list: List<Music>) {
        binding.ivNoResult.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}