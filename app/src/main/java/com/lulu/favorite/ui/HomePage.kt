package com.lulu.favorite.ui

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lulu.favorite.R
import com.lulu.favorite.ui.utils.Constants
import com.lulu.favorite.ui.utils.okhttp.Cline
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


@Preview
@Composable
fun HomePage(
    modifier : Modifier = Modifier,
//    navController : NavController
) {
    //初始化文本变量
    var text by remember { mutableStateOf("") }

    val widthPhone = (getApplicationScreenWidth(context = LocalContext.current)/3).dp

    Row(
        modifier = Modifier.width(getApplicationScreenWidth(context = LocalContext.current).dp),
        verticalAlignment = Alignment.CenterVertically,//垂直居中
        horizontalArrangement = Arrangement.Center //水平居中
    ) {
//        TextField(value = text,
//            onValueChange = {text = it} ,
//            shape = RoundedCornerShape(50.dp) ,
//            colors = TextFieldDefaults.textFieldColors(
//
//            )
//        )
        OutlinedTextField(
            modifier = Modifier.width(widthPhone),
            value = text,
            onValueChange = {  text = it },//监听文本变化
            label = { Text(text = stringResource(id = R.string.Enter)) },
            singleLine = true,
            trailingIcon = @Composable {//输入框末尾图标
                if (text.isNotEmpty()) {
                    Image(imageVector = Icons.Filled.Clear,
                        contentDescription = "",
                        modifier = Modifier.clickable { text = "" }
                    )
                }else{
                    Image(imageVector = Icons.Filled.Search,
                        contentDescription = ""
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),//自定义回车为搜索操作
            keyboardActions = KeyboardActions(//将搜索事件自定义
                onSearch = {
                     val textEnter = textEnter(text)
                    Log.d("TAG", "HomePage: $textEnter")
//                    navController.navigate("Articles")
                }
            ),
        )

    }

}

@Composable
fun demoFun() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}


/**
 * 键盘回车事件
 * */
fun textEnter(text : String) : String {
    var s : String = ""
    Log.d("TAG", "textEnter:  $text ")
//    val callback = object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            Log.d("TAG", "onFailure: $e")
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            var s = response.body?.string()
//            Log.d("TAG", "onResponse:  $s")
//        }
//    }
//    Cl("http://192.168.2.157:8080/maven_app/selectAllFiles", "json", callback)

    Cline("${Constants.Url}selectAllFiles", "json", object : Callback{

        override fun onFailure(call: Call, e: IOException) {
            Log.d("TAG", "onFailure: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            s = response.body?.string().toString()
            Log.d("TAG", "onResponse:  $s")
        }
    })
    return s

}


/**
 * 上移动画
 * */
fun moveOutlinedTextField(){

}

/**
 * 获取应用的屏幕参数DisplayMetrics
 * DisplayMetrics还可以获取一些物理像素、比例之类的数据
 */
private fun getDisplayMetrics(context: Context): DisplayMetrics {
    return context.resources.displayMetrics
}


/**
 * 获取应用屏幕的宽度
 */
fun getApplicationScreenWidth(context: Context): Int {
    return getDisplayMetrics(context).widthPixels
}

