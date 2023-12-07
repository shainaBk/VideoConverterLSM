package com.mobiledevices.videoconverter.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mobiledevices.videoconverter.Model.Music

class MusicViewModel : ViewModel() {
    companion object {
        private const val TAG = "MusicViewModel"
    }

    /**
     * We use the view model to store the music list during the fragment lifecycle
     */
    private val _musicList = MutableLiveData<MutableList<Music>>(mutableListOf())

    /**
     * Expose the music list as a live data
     */
    val musicList: LiveData<List<Music>>
        get() = _musicList.map { it.toList() }
    val nonDownloadedMusic: LiveData<List<Music>>
        get() = _musicList.map { music -> music.filter { !it.isDownloaded } }
    val downloadedMusic: LiveData<List<Music>>
        get() = _musicList.map { music -> music.filter { it.isDownloaded } }





    /**
     * Add a music to the music list
     * @param music The music to add
     */
    fun addMusic(music: Music) {
        val currentList = _musicList.value ?: mutableListOf()
        currentList.add(music)
        _musicList.postValue(currentList) // Update the mutable live data
        Log.d(TAG, "Music added: $music")
        Log.d(TAG, "Current music list: $currentList")
    }

    /**
     * Mark a music as downloaded
     * @param music The music to mark as downloaded
     */
    fun markMusicAsDownloaded(music: Music) {
        val currentList = _musicList.value ?: mutableListOf()
        currentList.find { it.videoId == music.videoId }?.isDownloaded = true
        _musicList.postValue(currentList) // Update the mutable live data
        Log.d(TAG, "Music marked as downloaded: $music")
        Log.d(TAG, "Updated music list: $currentList")
    }

    /**
     * Remove all music from the music list
     */
    fun clearMusicList() {
        _musicList.value?.clear()
        Log.d(TAG, "Music list cleared")
    }
}