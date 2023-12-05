package com.mobiledevices.videoconverter

import com.mobiledevices.videoconverter.Model.Music
import org.junit.Test
import kotlin.test.assertEquals

class MusicTest {

    @Test
    fun getMusicListTest() {
        //val mockService = mock(IMusicService::class.java)
        var listMusic = listOf<Music>(
            Music(
                "11",
                "urlTest",
                "chemin/chemin/chemin",
                "Shashou - qui chante",
                "truc Channel"
            )
        )
        assertEquals("Shashou - qui chante", listMusic[0].title)
        assertEquals("11", listMusic[0].videoId)
        assertEquals("urlTest", listMusic[0].videoUrl)
        assertEquals("chemin/chemin/chemin", listMusic[0].thumbnailUrl)
        assertEquals("truc Channel", listMusic[0].channelTitle)

    }

}