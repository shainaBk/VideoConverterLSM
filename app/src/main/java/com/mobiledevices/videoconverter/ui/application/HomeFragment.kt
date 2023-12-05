package com.mobiledevices.videoconverter.ui.activity.application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.adapter.MusicHomeAdapter
import com.mobiledevices.videoconverter.databinding.FragmentHomeBinding
import com.mobiledevices.videoconverter.viewModel.MusicViewModel
import kotlin.random.Random

class HomeFragment : Fragment(), MusicHomeAdapter.OnMusicDownloadListener {

    companion object {
        private const val TAG = "HomeFragment"
    }

    /**
     * We use the view binding feature to avoid using findViewById()
     */
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    /**
     * We use the view model to store the music list during the fragment lifecycle
     */
    private val viewModel: MusicViewModel by activityViewModels()

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
        Log.d(TAG, "Home fragment created")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeMusicList()
        setupSearchButton()

        return binding.root
    }

    /**
     * Destroy the view binding when the fragment view is destroyed
     */
    override fun onDestroyView() {
        Log.d(TAG, "Home fragment destroyed")
        super.onDestroyView()
        _binding = null
    }

    /**
     * Mark a music as downloaded
     * @param music The music to mark as downloaded
     */
    override fun onMusicDownload(music: Music) {
        Log.d(TAG, "Music downloaded: $music")
        viewModel.markMusicAsDownloaded(music)
    }

    /**
     * Observe the music list to update the recycler view adapter
     */
    private fun setupRecyclerView() {
        Log.d(TAG, "Setup HOME recycler view")
        val adapter = MusicHomeAdapter(mutableListOf(), this)
        binding.rvDownload.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    /**
     * Observe the music list to update the recycler view adapter
     */
    private fun observeMusicList() {
        Log.d(TAG, "Observe music list")
        viewModel.nonDownloadedMusic.observe(viewLifecycleOwner, Observer { nonDownloadedList ->
            binding.rvDownload.adapter?.let { adapter ->
                if (adapter is MusicHomeAdapter) {
                    val musicListString = nonDownloadedList.joinToString("\n") { it.toString() }
                    Log.d(TAG, "Update music list:\n$musicListString")
                    adapter.updateMusicList(nonDownloadedList)
                    updateNoResultImage(nonDownloadedList)
                }
            }
        })
    }

    /**
     * Setup the search button to open the search dialog
     */
    private fun setupSearchButton() {
        binding.newSearch.setOnClickListener {
            openSearchDialog()
        }
    }

    /**
     * Open the search dialog to add a new music
     */
    private fun openSearchDialog() {
        Log.d(TAG, "Open search dialog")
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_search_music, null)
        val editText = dialogView.findViewById<EditText>(R.id.et_search_music)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.search_music))
            .setView(dialogView)
            .setPositiveButton("Search") { _, _ ->
                val musicName = editText.text.toString()
                val newMusic =
                    Music(
                        Random.nextInt(1, 100000).toString(),
                        musicName,
                        musicName,
                        musicName,
                        musicName
                    )
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