package com.lulu.favorite.ui.utils.okhttp

import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit


class Cline {
    private var client: OkHttpClient? = null
    fun getClient(url: String, json: String, callback: Callback) {
        client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
        val requestBody: RequestBody =
            RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        val request: Request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        client!!.newCall(request).enqueue(callback)
    }

}
