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
        val downloaded: String = if (isDownloaded) "downloaded" else "NOTDownloaded"
        val favorite: String = if (isFavorite) "favorite" else "NOTFavorite"
        return "> $title by $channelTitle - {$downloaded, $favorite}"
    }
}