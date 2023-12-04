package com.mobiledevices.videoconverter.Model

import kotlinx.serialization.Serializable

@Serializable
class Music(
    val videoId: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val title: String = "",
    val channelTitle: String = "",
    var isFavorite: Boolean = false,
    var isDownloaded: Boolean = false
) {
    override fun toString(): String {
        return "Music: {$channelTitle} - {$title} - Downloaded: {$isDownloaded}"
    }
}