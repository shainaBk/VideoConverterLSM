package com.mobiledevices.videoconverter.ui.application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.databinding.FragmentHomeBinding
import com.mobiledevices.videoconverter.model.Music
import com.mobiledevices.videoconverter.ui.adapter.MusicHomeAdapter
import com.mobiledevices.videoconverter.viewModel.MusicViewModel
import com.mobiledevices.videoconverter.viewModel.SharedViewModel


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
    private val musicViewModel: MusicViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
        musicViewModel.markMusicAsDownloaded(music)
    }

    /**
     * Observe the music list to update the recycler view adapter
     */
    private fun setupRecyclerView() {
        Log.d(TAG, "Setup HOME recycler view")
        val adapter = MusicHomeAdapter(
            mutableListOf(),
            this
        ) { music ->
            sharedViewModel.addMusicToLibrary(music)
        }
        binding.rvDownload.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    /**
     * Observe the music list to update the recycler view adapter
     * TODO: change to currentUser list
     */
    private fun observeMusicList() {
        Log.d(TAG, "Observe music list")
        musicViewModel.nonDownloadedMusic.observe(
            viewLifecycleOwner
        ) { nonDownloadedList ->
            binding.rvDownload.adapter?.let { adapter ->
                if (adapter is MusicHomeAdapter) {
                    val musicListString = nonDownloadedList.joinToString("\n") { it.toString() }
                    Log.d(TAG, "Update music list:\n$musicListString")
                    adapter.updateMusicList(nonDownloadedList)
                    updateNoResultImage(nonDownloadedList)
                }
            }
        }
    }

    /**
     * Setup the search button to open the search dialog
     */
    private fun setupSearchButton() {
        binding.newSearch.setOnClickListener {
            if (isConnected()) {
                openSearchDialog()
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("No internet connection")
                    .setMessage("Please check your internet connection")
                    .setPositiveButton("OK", null)
                    .show()
            }
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
                //NEW
                musicViewModel.searchMusique(editText.text.toString(),
                    { mS ->
                        musicViewModel.setMusicList(mS)
                    }, { isValid, message ->
                        if (isValid) {
                            Log.i(TAG, "Music found: $message")
                        } else {
                            Log.e(TAG, "Error searching music: $message")
                        }
                    })
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

    private fun isConnected(): Boolean {
        // return true // Uncomment if the internet test is not working
        val command = "ping -c 1 google.com"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    }
}