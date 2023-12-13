package com.mobiledevices.videoconverter.model

import kotlinx.serialization.Serializable

@Serializable
class Music(
    val videoId: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val title: String = "",
    val channelTitle: String = "",
    var isDownloaded: Boolean = false
) {
    override fun toString(): String {
        return "> $title by $channelTitle"
    }
}