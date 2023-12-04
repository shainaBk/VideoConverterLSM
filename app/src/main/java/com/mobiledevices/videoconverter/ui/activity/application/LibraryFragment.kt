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