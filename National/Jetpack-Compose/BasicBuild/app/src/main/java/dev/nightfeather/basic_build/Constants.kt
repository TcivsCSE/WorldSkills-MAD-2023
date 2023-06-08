package dev.nightfeather.basic_build

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

object Constants {
    const val baseUrl = "http://10.0.2.2:8000"
    final val contentTypeJson = "application/json; charset=utf-8".toMediaType()
}