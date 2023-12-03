package com.mobiledevices.videoconverter.ui.activity.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.adapter.MusicLibraryAdapter
import com.mobiledevices.videoconverter.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        val musicList = ArrayList<Music>()

        musicList.add(Music("", "", "", "Title 1", "Artist 1"))
        musicList.add(Music("", "", "", "Title 2", "Artist 2"))
        musicList.add(Music("", "", "", "Title 3", "Artist 3"))
        musicList.add(Music("", "", "", "Title 4", "Artist 4"))
        musicList.add(Music("", "", "", "Title 5", "Artist 5"))
        musicList.add(Music("", "", "", "Title 6", "Artist 6"))
        musicList.add(Music("", "", "", "Title 7", "Artist 7"))
        musicList.add(Music("", "", "", "Title 8", "Artist 8"))

        val recyclerView: RecyclerView = binding.rvLibrary
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MusicLibraryAdapter(musicList)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}