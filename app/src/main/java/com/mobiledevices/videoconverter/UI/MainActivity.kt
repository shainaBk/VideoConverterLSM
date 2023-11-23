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
    }

}