package com.mobiledevices.videoconverter.Ui.application

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.Ui.adapter.MusicLibraryAdapter
import com.mobiledevices.videoconverter.databinding.FragmentLibraryBinding
import com.mobiledevices.videoconverter.viewModel.MusicViewModel

class LibraryFragment : Fragment() {

    companion object {
        private const val TAG = "LibraryFragment"
    }

    /**
     * We use the view binding feature to avoid using findViewById()
     */
    private var _binding: FragmentLibraryBinding? = null
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
        Log.d(TAG, "Library fragment created")
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeDownloadedMusic()

        return binding.root
    }

    /**
     * Destroy the view binding when the fragment view is destroyed
     */
    override fun onDestroyView() {
        Log.d(TAG, "Library fragment destroyed")
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        val adapter = MusicLibraryAdapter(mutableListOf())
        Log.d(TAG, "Setup LIB recycler view")
        binding.rvLibrary.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun observeDownloadedMusic() {
        viewModel.downloadedMusic.observe(viewLifecycleOwner, Observer { downloadedList ->
            binding.rvLibrary.adapter?.let { adapter ->
                if (adapter is MusicLibraryAdapter) {
                    val musicListString = downloadedList.joinToString("\n") { it.toString() }
                    Log.d(TAG, "Update music list:\n$musicListString")
                    adapter.updateMusicList(downloadedList)
                    updateNoResultImage(downloadedList)
                }
            }
        })
    }

    /**
     * Update the no result image visibility
     * @param list The music list
     */
    private fun updateNoResultImage(list: List<Music>) {
        binding.ivNoResult.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}