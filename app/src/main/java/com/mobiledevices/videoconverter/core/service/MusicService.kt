package com.mobiledevices.videoconverter.core.service

import android.os.Environment
import android.util.Log
import com.chaquo.python.Python
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobiledevices.videoconverter.model.Music

class MusicService {
    companion object {
        private val python = Python.getInstance()
        private val pythonScript = python.getModule("youtubeToMp3")
        private const val TAG = "MusicService"

        /**
         * Récupère une liste de musiques à partir d'une recherche YouTube.
         * @param search La chaîne de recherche pour trouver des musiques sur YouTube.
         * @return Une liste de musiques correspondant aux critères de recherche.
         */
        fun getMusics(search: String): List<Music> {
            return try {
                val jsonResult = pythonScript.callAttr("getMusics", search, 7).toString()
                Log.d(TAG, "here=> $jsonResult")
                val type = object : TypeToken<List<Music>>() {}.type
                Gson().fromJson(jsonResult, type)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting musics from YouTube", e)
                emptyList()
            }
        }

        /**
         * Télécharge une musique depuis YouTube.
         * @param url L'URL de la musique sur YouTube à télécharger.
         * NOTE: Cette fonction nécessite une implementation complète.
         * De plus, elle n'est pas fonctionnel sur l'émulateur (ssl issues)
         */
        //TODO: try to fix it
        fun downloadMusic(url: String) {
            val downloadPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
            pythonScript.callAttr("downloadMusics", url, downloadPath)
        }
    }
}