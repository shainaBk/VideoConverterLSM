package com.mobiledevices.videoconverter.Core.Service
import android.os.Environment
import android.util.Log
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.mobiledevices.videoconverter.Model.Music
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
public class MusicService {
    companion object {
        val python = Python.getInstance()
        val pythonScript = python.getModule("youtubeToMp3")

        /**
         * Récupère une liste de musiques à partir d'une recherche YouTube.
         * @param search La chaîne de recherche pour trouver des musiques sur YouTube.
         * @return Une liste de musiques correspondant aux critères de recherche.
         */
        fun getMusics(search: String): List<Music> {
            return try {
                val jsonResult = pythonScript.callAttr("getMusics", search, 7).toString()
                Log.d("JSONRESULT", "here=> "+ jsonResult)
                val type = object : TypeToken<List<Music>>() {}.type
                return Gson().fromJson(jsonResult, type)
            } catch (e: Exception) {
                Log.e("MusicService", "Erreur: ${e.message}")
                return emptyList()
            }
        }


        /**
         * Télécharge une musique depuis YouTube.
         * @param url L'URL de la musique sur YouTube à télécharger.
         * NOTE: Cette fonction nécessite une implémentation complète.
         * De plus, elle n'est pas fonctionnel sur l'emulateur (ssl issues)
         */
        //TODO: fix that shit !!
        fun downloadMusic(url:String){
            //TODO: Change to specific repertory
            val downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
            pythonScript.callAttr("downloadMusics",url,downloadPath)
        }
    }




}