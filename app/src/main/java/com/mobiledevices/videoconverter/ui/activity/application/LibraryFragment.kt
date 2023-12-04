package com.mobiledevices.videoconverter.ui.activity.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.adapter.MusicLibraryAdapter
import com.mobiledevices.videoconverter.databinding.FragmentLibraryBinding
import com.mobiledevices.videoconverter.viewModel.MusicViewModel

class LibraryFragment : Fragment(), MusicLibraryAdapter.OnMusicDownloadListener {
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
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeDownloadedMusic()
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
     */
    override fun onMusicDownload(music: Music) {
        viewModel.markMusicAsDownloaded(music)
    }

    private fun setupRecyclerView() {
        val adapter = MusicLibraryAdapter(mutableListOf(), this)
        binding.rvLibrary.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
    }

    private fun observeDownloadedMusic() {
        viewModel.downloadedMusic.observe(viewLifecycleOwner, Observer { downloadedList ->
            binding.rvLibrary.adapter?.let { adapter ->
                if (adapter is MusicLibraryAdapter) {
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