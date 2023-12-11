package com.mobiledevices.videoconverter

import com.mobiledevices.videoconverter.model.Music
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
            ),
            Music(
                "videoId1",
                "http://example.com",
                "http://imageurl.com",
                "Title",
                "Channel",
                true
            )
        )
        assertEquals("Shashou - qui chante", listMusic[0].title)
        assertEquals("11", listMusic[0].videoId)
        assertEquals("urlTest", listMusic[0].videoUrl)
        assertEquals("chemin/chemin/chemin", listMusic[0].thumbnailUrl)
        assertEquals("truc Channel", listMusic[0].channelTitle)

        assertEquals("Title", listMusic[1].title)
        assertEquals("Channel", listMusic[1].channelTitle)
        assertEquals("http://imageurl.com", listMusic[1].thumbnailUrl)

    }

    @Test
    fun testMusicToString() {
        val music = Music(title = "Title", channelTitle = "Channel")
        val expectedString = "> Title by Channel"

        assertEquals(expectedString, music.toString())
    }


}