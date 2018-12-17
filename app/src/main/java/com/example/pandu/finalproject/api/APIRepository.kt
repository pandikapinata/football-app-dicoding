package com.example.pandu.finalproject.api

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class APIRepository {
    fun doRequest(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}