package com.mobiledevices.videoconverter.Model

import kotlinx.serialization.Serializable
@Serializable
data class User(val id: String, val mail: String, val password: String)
