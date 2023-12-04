package com.mobiledevices.videoconverter.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiledevices.videoconverter.Model.Music

class MusicViewModel : ViewModel() {
    // We use the view model to store the music list during the fragment lifecycle
    // 'by lazy' means that the variable will be initialized when it will be used for the first time
    val musicList: MutableLiveData<MutableList<Music>> by lazy { MutableLiveData(mutableListOf()) }

    /**
     * Add a music to the music list
     * @param music The music to add
     */
    fun addMusic(music: Music) {
        val currentList = musicList.value ?: mutableListOf()
        currentList.add(music)
        musicList.postValue(currentList) // Post the new value using thread-safe way
    }

    /**
     * Get the list of non-downloaded music
     */
    fun getNonDownloadedMusic(): List<Music> {
        return musicList.value?.filter { !it.isDownloaded } ?: listOf()
    }

    /**
     * Mark a music as downloaded
     * @param music The music to mark as downloaded
     */
    fun markMusicAsDownloaded(music: Music) {
        val currentList = musicList.value ?: mutableListOf()
        currentList.find { it.videoId == music.videoId }?.isDownloaded = true
        musicList.postValue(currentList.filter { !it.isDownloaded }.toMutableList())
    }
}