package com.mobiledevices.videoconverter.Model

import kotlinx.serialization.Serializable

@Serializable
class Music(val video_id:String, val video_url: String,val thumbnail_url:String, val title: String, val channel_title: String)