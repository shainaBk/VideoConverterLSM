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

        fun getMusics(search: String): List<Music> {
            return try {
                val jsonResult = pythonScript.callAttr("getMusics", search).toString()
                Log.d("JSONRESULT", "here=> "+ jsonResult)
                val type = object : TypeToken<List<Music>>() {}.type
                return Gson().fromJson(jsonResult, type)
            } catch (e: Exception) {
                Log.e("MusicService", "Erreur: ${e.message}")
                return emptyList()
            }
        }

        //TODO: fix that shit !!
        fun downloadMusic(url:String){
            //TODO: Change to specific repertory
            val downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
            pythonScript.callAttr("downloadMusics",url,downloadPath)
        }
    }




}