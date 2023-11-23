package com.mobiledevices.videoconverter.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.chaquo.python.Python
import com.mobiledevices.videoconverter.R
import android.util.Log
import com.mobiledevices.videoconverter.Service.MusicService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        /**PART RECUP VIDEOS**/
        val result = MusicService.getMusics("Brent Fayaz")
        //val result = pythonScript.callAttr("getMusics","Brent Fayaz").toString()
        // Afficher dans les logs
        if (result.isNotEmpty()) {
            for (i in 0 until result.size) {
                Log.d("PythonTest", "Thumbnail URL of Music $i => ${result[i].title}")
                if (i == 4) break // Arrêtez la boucle après avoir traité les 5 premiers éléments
            }
        } else {
            Log.d("PythonTest", "Pas de résultats")
        }


        //TODO: FIX THAT PART!!!
        /*
        /**TEST CHAQOPY**/
        val python = Python.getInstance()
        val pythonScript = python.getModule("youtubeToMp3")
        /**PART DOWNLOAD SONG**/
        val downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        //val internalStoragePath = getFilesDir()
        Log.d("PythonTest2",downloadPath)

        pythonScript.callAttr("create_file",downloadPath,"fululu.txt","testSmèere")
        //pythonScript.callAttr("downloadMusics",result,internalStoragePath)
        Log.d("PythonTest2","Download DONE")*/

    }

}