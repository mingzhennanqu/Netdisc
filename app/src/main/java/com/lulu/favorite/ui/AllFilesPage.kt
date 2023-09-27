package com.lulu.favorite.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lulu.favorite.ui.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


@Composable
fun RequestWithProgress(name : String) {
    val progress = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val requestBody: RequestBody =
            RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), name)
        // 在此处进行 OkHttp 请求并获取进度
        val request = Request.Builder()
            .url("${Constants.Url}/selectFlies")
            .post(requestBody)
            .build()
        val client = OkHttpClient()
        val call = client.newCall(request)

        withContext(Dispatchers.IO) {
            val response = call.execute()
            if (response.isSuccessful) {
                val responseBody = response.body
                if (responseBody != null) {
                    val totalSize = responseBody.contentLength()
                    val buffer = ByteArray(8192)
                    var bytesRead = 0L
                    var read: Int
                    val inputStream = responseBody.byteStream()

                    while (inputStream.read(buffer).also { read = it } != -1) {
                        bytesRead += read
                        val progressValue = (bytesRead * 100 / totalSize).toInt()
                        progress.value = progressValue
                    }

                    inputStream.close()
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            progress = progress.value.toFloat() / 100f
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Progress: ${progress.value}%"
        )
    }
}
