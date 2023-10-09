package com.lulu.favorite.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.lulu.favorite.MainActivity
import com.lulu.favorite.R
import com.lulu.favorite.data.HomePageFile
import com.lulu.favorite.data.HomePageFileAll
import com.lulu.favorite.ui.utils.Constants
import com.lulu.favorite.ui.utils.okhttp.clineParameter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.system.exitProcess

/**
 * 这是所有数据展示页面
 * */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllFilesPageFirst(
    controller: NavHostController,
    folders: List<HomePageFile>?,
) {

    //用于刷新ui的state
    val refreshTrigger = remember { mutableStateOf(true) }

    //用于展示文件列表的List
    val rememberedFolders = remember { mutableStateOf(folders) }

    //用于展示tag层级的list
    val remHomeTagList = remember { mutableListOf("Home") }

    var showExitHint by remember { mutableStateOf(false) }

    var scope = rememberCoroutineScope()

    var snackbarHostState = remember { SnackbarHostState() }

    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()


    //挂起函数,用于监听state，刷新UI
    LaunchedEffect(refreshTrigger.value) {
        // 在这里执行手动刷新的操作
        refreshTrigger.value = !refreshTrigger.value
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent
    ){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
//            .clickable {
//                controller.popBackStack()
//            }
    ) {
        Row() {
            remHomeTagList.forEach {
                Box(
//                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.tag),
                        contentDescription = null,
                        modifier = Modifier
                            .width(40.dp)
//                            .clickable {
//                                Log.d("TAG", "AllFilesPageFirst: $it")
//                                remHomeTagList.subList(0 , 2)
//                                rememberedFolders.value = findFilesForNameByType(it)
////                                remHomeTagList.subList(0 , remHomeTagList.indexOf(it))
//                            }
                    )
                    Text(text = it, fontSize = 5.sp)

                }

            }
        }

        rememberedFolders.value?.forEach { file ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        when (file.file_type) {
                            "folders" -> {
                                rememberedFolders.value = findFilesForNameByType(file.name)
                                remHomeTagList.add(file.name)
                                Log.d("TAG", "AllFilesPageFirst: $remHomeTagList")
                            }

                            "txt" ->
                                Log.d("TAG", "AllFilesPageFirst: 这是${file.name}")
                        }

                        suspend {
                            delay(500)
                            refreshTrigger.value = !refreshTrigger.value
                        }

                    },

//                horizontalArrangement = Arrangement.Center,//垂直居中
                verticalAlignment = Alignment.CenterVertically,//水平居中
            ) {
                when (file.file_type) {
                    "folders" ->
                        Image(
                            painter = painterResource(id = R.drawable.folder),
                            contentDescription = null,
                            modifier = Modifier
                                .width(40.dp)
                                .padding(10.dp),
                        )

                    "txt" ->
                        Image(
                            painter = painterResource(id = R.drawable.txt),
                            contentDescription = null,
                            modifier = Modifier
                                .width(40.dp)
                                .padding(10.dp),
                        )
                }
                Text(
                    text = file.name,
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



    }
    //系统导航返回键
    BackHandler(enabled = true) {
        Log.e("tag", "返回键被点击 $remHomeTagList")
        if (remHomeTagList.last() == "Home") {
            Log.d("TAG", "AllFilesPageFirst: it is last item")
            if (showExitHint) {
                val activity: MainActivity = MainActivity()
                activity.finish()
                //以参数0作为退出状态码，来终止整个应用程序的运行
                exitProcess(0)
            }
            // 将 showExitHint 的值设置为 true，表示 Snackbar 已经出现
            showExitHint = true
            scope.launch {
                Log.d("TAG", "test: test")
                // 当 Snackbar 消失时，改变 showExitHint 的值
                showExitHint =
                    scaffoldState.snackbarHostState.showSnackbar("再按一次以退出") != SnackbarResult.Dismissed

            }

        } else {
            remHomeTagList.removeLast()
            rememberedFolders.value = findFilesForNameByType(remHomeTagList.last())
        }

        suspend {
            delay(500)
            refreshTrigger.value = !refreshTrigger.value
        }

    }


}



val gson = Gson()

/**
 * 根据传入参数查询
 * */
fun findFilesForNameByType(name: String): List<HomePageFile> {
    val list = mutableListOf<HomePageFile>()

    clineParameter("${Constants.Url}selectFliesForName", name, object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.d("TAG", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string()
            Log.d("TAG", "selectFliesForNameOnResponse: $responseData")
            val fromJson = gson.fromJson(responseData, HomePageFileAll::class.java)
            Log.d("TAG", "onResponse_fromJson: $fromJson")
            if (fromJson.code == "SUCCESS") {
                for (i in fromJson.body.indices) {
                    list.add(fromJson.body[i])
                }
                Log.d("TAG", "onResponseList: $list")
            } else {
                Log.d("TAG", "onResponse: ${fromJson.msg}")
            }

        }

    })


    return list
}


