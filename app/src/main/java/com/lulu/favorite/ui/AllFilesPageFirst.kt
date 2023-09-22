package com.lulu.favorite.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.lulu.favorite.R
import com.lulu.favorite.data.HomePageFile1
import com.lulu.favorite.ui.utils.Constants
import com.lulu.favorite.ui.utils.okhttp.Cline
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


/**
 * 这是所有数据展示页面
 * */
@Composable
fun AllFilesPageFirst(
    folders: List<String>?,
    isShowHomePage : Boolean,
    setIsShowHomePage : (Boolean) -> Unit
) {
    val rememberedFolders = remember { mutableStateOf(folders) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
            rememberedFolders.value?.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
//                            if (isShowHomePage) {
//                                setIsShowHomePage(false)
//                            }
                            clickAbleFolders(it)

//                            folders = findFilesForName(it)
                        },
//                horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
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
}

/**
 * 查询home文件
 * */
fun findHomeFolder(): List<String>? {
    val list= mutableListOf<String>()
    val gson = Gson()

    Cline("${Constants.Url}selectAllHomeFiles", "json", object : Callback {

        override fun onFailure(call: Call, e: IOException) {
            Log.d("TAG", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string()
            Log.d("TAG", "respData: $responseData")
            val fromJson = gson.fromJson(responseData, HomePageFile1::class.java)
            if (fromJson.code == "SUCCESS"){
                for (i in fromJson.body.indices){
                    list.add(fromJson.body[i].name)
                }
            }else{
                Log.d("TAG", "onResponse: ${fromJson.msg}")
            }
            Log.d("TAG", "list: $list")
        }
    })
    return list
}

fun findFilesForName(name : String ) : List<String> {
    val list= mutableListOf<String>()

    Cline("${Constants.Url}selectFliesForName" , name , object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call, response: Response) {
            TODO("Not yet implemented")
        }

    })


    return list
}

fun clickAbleFolders(folderName : String){
    Log.d("TAG", "clickAbleFolders: $folderName")
    if (folderName == "pdf"){

    }
}
