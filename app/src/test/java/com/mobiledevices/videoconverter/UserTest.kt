package com.mobiledevices.videoconverter

import com.mobiledevices.videoconverter.model.Music
import com.mobiledevices.videoconverter.model.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {

    @Test
    fun testAddMusic() {
        val user = User(id = "123", librarie = listOf())
        val music = Music(videoId = "videoId1")
        val updatedUser = user.addMusic(music)

        assertEquals(1, updatedUser.librarie.size)
        assertEquals("videoId1", updatedUser.librarie[0].videoId)
    }

    @Test
    fun testChangePassword() {
        val user = User(password = "oldPassword")
        val updatedUser = user.changePassword("newPassword")

        assertEquals("newPassword", updatedUser.password)
    }
}