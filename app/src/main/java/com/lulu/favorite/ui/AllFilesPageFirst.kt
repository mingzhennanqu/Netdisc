package com.lulu.favorite.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.lulu.favorite.R
import com.lulu.favorite.data.HomePageFileAll
import com.lulu.favorite.ui.utils.Constants
import com.lulu.favorite.ui.utils.okhttp.cline
import com.lulu.favorite.ui.utils.okhttp.clineParameter
import kotlinx.coroutines.delay
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


/**
 * 这是所有数据展示页面
 * */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllFilesPageFirst(
    controller : NavHostController,
    folders: List<String>?,
) {
    val refreshTrigger = remember { mutableStateOf(true) }
    var rememberedFolders = remember { mutableStateOf(folders) }
    val rem = remember { mutableStateOf(findAllFolder()) }

    LaunchedEffect(refreshTrigger.value) {
        // 在这里执行手动刷新的操作
        refreshTrigger.value = !refreshTrigger.value
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .clickable {
                controller.popBackStack()
            }
    ) {
        rememberedFolders.value?.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        Log.d("TAG", "AllFilesPageFirst: ${rem.value}")
                        rememberedFolders.value = findFilesForName(it)

                        suspend {
                            delay(500)
                            refreshTrigger.value = !refreshTrigger.value
                        }

                    },

//                horizontalArrangement = Arrangement.Center,//垂直居中
                verticalAlignment = Alignment.CenterVertically,//水平居中
            ) {
                Image(
                    painter = painterResource(id = R.drawable.folder),
                    contentDescription = null,
                    modifier = Modifier
                        .width(40.dp)
                        .padding(10.dp),
                )
                Text(
                    text = it,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
            Divider(
                //设置分割线的高度
                thickness = 0.5.dp,
                //设置分割线的颜色
                color = Color.Red,
            )
        }

    }
    BackHandler(enabled = true) {
        Log.e("tag","返回键被点击")
    }
}

/**
 * 查询home文件
 * */
fun findHomeFolder(): List<String>? {
    val list = mutableListOf<String>()
    val gson = Gson()

    cline("${Constants.Url}selectAllHomeFiles", "", object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            Log.d("TAG", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string()
            Log.d("TAG", "respData: $responseData")
            val fromJson = gson.fromJson(responseData, HomePageFileAll::class.java)
            if (fromJson.code == "SUCCESS") {
                for (i in fromJson.body.indices) {
                    list.add(fromJson.body[i].name)
                }
            } else {
                Log.d("TAG", "onResponse: ${fromJson.msg}")
            }
            Log.d("TAG", "list: $list")
        }
    })
    return list
}

fun findFilesForName(name: String): List<String> {
    val list = mutableListOf<String>()
    val gson = Gson()

    clineParameter("${Constants.Url}selectFliesForName", name, object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.d("TAG", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string()
            Log.d("TAG", "selectFliesForNameOnResponse: $responseData")
            val fromJson = gson.fromJson(responseData, HomePageFileAll::class.java)
            if (fromJson.code == "SUCCESS") {
                for (i in fromJson.body.indices) {
                    list.add(fromJson.body[i].name)
                }
                Log.d("TAG", "onResponseList: $list")
            } else {
                Log.d("TAG", "onResponse: ${fromJson.msg}")
            }

        }

    })


    return list
}


/**
 * 查询home文件
 * */
fun findAllFolder(): HomePageFileAll? {
    val list = mutableListOf<String>()
    var from: HomePageFileAll? = null
    val gson = Gson()

    cline("${Constants.Url}selectFlies", "", object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            Log.d("TAG", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string()
            Log.d("TAG", "respData: $responseData")
            val fromJson = gson.fromJson(responseData, HomePageFileAll::class.java)
            from = fromJson
            if (fromJson.code == "SUCCESS") {
                for (i in fromJson.body.indices) {
                    list.add(fromJson.body[i].name)
                }
            } else {
                Log.d("TAG", "onResponse: ${fromJson.msg}")
            }
            Log.d("TAG", "list: $list")
        }
    })
    Log.d("TAG", "findAllFolder: $from")
    return from
}

fun clickAbleFolders(folderName: String) {
    Log.d("TAG", "clickAbleFolders: $folderName")

    if (folderName == "pdf") {

    }
}


