package com.lulu.favorite.utils.okhttp

import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit

private var client: OkHttpClient? = null
fun cline(url: String, json: String, callback: Callback?){
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
    client!!.newCall(request).enqueue(callback!!)
}

fun clineParameter(url: String, json: String, callback: Callback?){
    client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
    val requestBody: RequestBody = FormBody.Builder()
        .add("name" , json)
        .build()
    val request: Request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()
    client!!.newCall(request).enqueue(callback!!)
}

//object ProgressIntercept: Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val rawRequest = chain.request()
//        val uploadListener = rawRequest.tag(OkUploadListener::class.java)
//        val downloadListener = rawRequest.tag(OkDownloadListener::class.java)
//        // 替换请求 body 实现上传的进度监听
//        val request = replaceRequestBody(rawRequest, uploadListener)
//        val response = chain.proceed(request)
//        // 替换相应 body 实现下载的进度监听
//        return replaceResponseBody(response, downloadListener)
//    }
//
//    private fun replaceRequestBody(request: Request, listener: OkUploadListener?): Request {
//        val body = request.body
//        if (body == null || listener == null) {
//            return request
//        }
//        return request.newBuilder()
//            .method(request.method, ProgressRequestBody(body, listener))
//            .build()
//    }
//
//    private fun replaceResponseBody(response: Response, listener: OkDownloadListener?): Response {
//        val body = response.body
//        if (body == null || listener == null) {
//            return response
//        }
//        return response.newBuilder()
//            .body(ProgressResponseBody(body, listener))
//            .build()
//    }
//
//}
